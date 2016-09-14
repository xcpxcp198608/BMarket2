package com.px.bmarket.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.Data.AppData.IAppService;
import com.px.bmarket.F;
import com.px.bmarket.Presenter.BootActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.ApkInstall;
import com.px.bmarket.Utils.FileDownload.DownloadManager;
import com.px.bmarket.Utils.FileDownload.OnDownloadListener;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.SystemConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BootActivity extends BaseActivity<IBootActivity, BootActivityPresenter> implements IBootActivity {

    @BindView(R.id.tv_UpdateMessage)
    TextView tv_UpdateMessage;
    @BindView(R.id.bt_UpdateConfirm)
    Button bt_UpdateConfirm;
    @BindView(R.id.ll_Update)
    LinearLayout ll_Update;
    @BindView(R.id.pb_UpdateProgress)
    ProgressBar pb_UpdateProgress;
    @BindView(R.id.tv_progress)
    TextView tv_Progress;

    private AppMarketInfo mAppMarketInfo;
    private DownloadManager downloadManager;
    private boolean load= false;
    private SQLiteDao sqliteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        ButterKnife.bind(this);
        sqliteDao = SQLiteDao.getInstance(BootActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkDevice();
        presenter.dispatch();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(downloadManager!=null){
            downloadManager.pauseDownload();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(downloadManager!=null){
            downloadManager.pauseDownload();
        }
    }

    @Override
    protected BootActivityPresenter createPresenter() {
        return new BootActivityPresenter(this);
    }

    private void checkDevice() {
        if (!"BTVi3".equals(Build.MODEL) && !"MorphoBT E110".equals(Build.MODEL) && !"HM NOTE 1LTE".equals(Build.MODEL)
                && !"BTV3".equals(Build.MODEL)) {
            showWarningDialog(BootActivity.this, getString(R.string.warning_title), getString(R.string.warning_message1)
                    , getString(R.string.warning_exit));
            return;
        }
    }


    private void showWarningDialog(Context context, String title, String message, String negativeButtonText) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void checkUpdate(AppMarketInfo appMarketInfo) {
        Logger.d(appMarketInfo.toString());
        int localVerCode = ApkCheck.getInstalledApkVersionCode(BootActivity.this, getPackageName());
        if (localVerCode >= appMarketInfo.getApkVersionCode()) {
            loadAppInfo();
        } else {
            ll_Update.setVisibility(View.VISIBLE);
            tv_UpdateMessage.setText(appMarketInfo.getApkUpdateInfo());
            bt_UpdateConfirm.requestFocus();
            mAppMarketInfo = appMarketInfo;
        }
    }

    public void loadAppInfo() {
        new Retrofit.Builder()
                .baseUrl(F.url.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IAppService.class)
                .getData()
                .enqueue(new Callback<List<AppInfo>>() {
                    @Override
                    public void onResponse(Call<List<AppInfo>> call, Response<List<AppInfo>> response) {
                        Observable.from(response.body())
                                .subscribe(new Subscriber<AppInfo>() {
                                    @Override
                                    public void onCompleted() {
                                        //Logger.d("completed");
                                        startActivity(new Intent(BootActivity.this ,MainActivity.class));
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(AppInfo appInfo) {
                                        //Logger.d(appInfo.toString());
                                        sqliteDao.insertOrUpdateData(appInfo);
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<List<AppInfo>> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.bt_UpdateConfirm)
    public void onClick() {
        if (mAppMarketInfo != null) {
            ll_Update.setVisibility(View.GONE);
            pb_UpdateProgress.setVisibility(View.VISIBLE);
            tv_Progress.setVisibility(View.VISIBLE);
            updateAppMarket(mAppMarketInfo);
        }
    }

    private void updateAppMarket(final AppMarketInfo appMarketInfo) {
        Logger.d(appMarketInfo.toString());
        if(downloadManager == null){
            downloadManager = DownloadManager.getInstance(BootActivity.this);
        }
        downloadManager.startDownload(appMarketInfo.getApkFileName() ,appMarketInfo.getApkFileDownloadUrl() ,F.path.market);
        downloadManager.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onStart(int progress, boolean isStart) {

            }

            @Override
            public void onProgressChange(int progress) {
                pb_UpdateProgress.setProgress(progress);
                tv_Progress.setText(progress+"%");
            }

            @Override
            public void onPause(int progress) {

            }

            @Override
            public void onCompleted(int progress) {
                pb_UpdateProgress.setProgress(100);
                tv_Progress.setText(100+"%");
                pb_UpdateProgress.setVisibility(View.GONE);
                tv_Progress.setVisibility(View.GONE);
                if(ApkCheck.isApkCanInstalled(BootActivity.this,F.path.market ,appMarketInfo.getApkFileName())) {
                    ApkInstall.installApk(BootActivity.this, F.path.market, appMarketInfo.getApkFileName());
                }else{
                    SystemConfig.toastLong(BootActivity.this,getString(R.string.download_error));
                }
            }
        });

    }
}
