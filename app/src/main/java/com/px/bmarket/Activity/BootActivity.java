package com.px.bmarket.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.Data.IAppService;
import com.px.bmarket.F;
import com.px.bmarket.FileDownload.DownloadFileInfo;
import com.px.bmarket.FileDownload.DownloadManager;
import com.px.bmarket.FileDownload.DownloadStatusListener;
import com.px.bmarket.Presenter.BootActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.ApkInstall;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.SystemConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;


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
        int localVerCode = ApkCheck.getInstalledApkVersionCode(BootActivity.this, getPackageName());
        if (localVerCode >= appMarketInfo.getApkVersionCode()) {
            loadApp1();
        } else {
            ll_Update.setVisibility(View.VISIBLE);
            tv_UpdateMessage.setText(appMarketInfo.getApkUpdateInfo());
            bt_UpdateConfirm.requestFocus();
            mAppMarketInfo = appMarketInfo;
        }
    }

    public void loadApp(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(F.url.app_info, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if(jsonArray.length()>0){
                    try {
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        AppInfo appInfo = new AppInfo();
                        appInfo.setApkName(jsonObject.getString("apkName"));
                        appInfo.setApkFileName(jsonObject.getString("apkFileName"));
                        appInfo.setApkPackageName(jsonObject.getString("apkPackageName"));
                        appInfo.setApkIconUrl(jsonObject.getString("apkIconUrl"));
                        appInfo.setApkDownloadUrl(jsonObject.getString("apkDownloadUrl"));
                        appInfo.setApkVersion(jsonObject.getString("apkVersion"));
                        appInfo.setApkSize(jsonObject.getString("apkSize"));
                        appInfo.setApkType(jsonObject.getString("apkType"));
                        appInfo.setApkLanguage(jsonObject.getString("apkLanguage"));
                        appInfo.setApkSummary(jsonObject.getString("apkSummary"));
                        appInfo.setIsRecommend(jsonObject.getString("isRecommend"));
                        appInfo.setIsDisplay(jsonObject.getString("isDisplay"));
                        appInfo.setApkVersionCode(jsonObject.getInt("apkVersionCode"));
                        appInfo.setSequence(jsonObject.getInt("sequence"));
                        Logger.d(appInfo.toString());
                        sqliteDao.insertOrUpdateData(appInfo);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(BootActivity.this ,MainActivity.class));
                    finish();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logger.d(volleyError.getMessage());
            }
        });
        jsonArrayRequest.setTag("AppInfo");
        Application.getVolleyRequest().add(jsonArrayRequest);
    }

    public void loadApp1(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        new  Retrofit.Builder().baseUrl("http://158.69.229.104:8092/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IAppService.class)
                .getData()
                .enqueue(new Callback<List<AppInfo>>() {
                    @Override
                    public void onResponse(Call<List<AppInfo>> call, Response<List<AppInfo>> response) {
                        if(response.body().size()>0){
                            Observable.from(response.body())
                                    .subscribe(new Action1<AppInfo>() {
                                        @Override
                                        public void call(AppInfo appInfo) {
                                            Logger.d(appInfo.toString());
                                            sqliteDao.insertOrUpdateData(appInfo);
                                        }
                                    });
                            startActivity(new Intent(BootActivity.this ,MainActivity.class));
                            finish();
                        }
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
            downloadManager = new DownloadManager(BootActivity.this);
        }
        DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
        downloadFileInfo.setFileFullName(appMarketInfo.getApkFileName());
        downloadFileInfo.setFileDownloadUrl(appMarketInfo.getApkFileDownloadUrl());
        downloadManager.startDownload(downloadFileInfo ,F.path.market);
        downloadManager.setDownloadStatusListener(new DownloadStatusListener() {
            @Override
            public void startDownload(boolean isStart, long fileLength) {

            }

            @Override
            public void pauseDownload(boolean isPauseDownload, int progress) {
                
            }

            @Override
            public void downloadProgressChanged(boolean isDownloading, int progress) {
                pb_UpdateProgress.setProgress(progress);
                tv_Progress.setText(progress+"%");
            }

            @Override
            public void completedDownload(boolean isCompleted, int progress) {
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
