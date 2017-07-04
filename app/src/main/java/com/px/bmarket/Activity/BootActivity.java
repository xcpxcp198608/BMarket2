package com.px.bmarket.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.UpgradeInfo;
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
import com.px.bmarket.Utils.OkHttp.Listener.StringListener;
import com.px.bmarket.Utils.OkHttp.OkMaster;
import com.px.bmarket.Utils.SystemConfig;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private UpgradeInfo mUpgradeInfo;
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
        presenter.checkUpdate();
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
    public void checkUpdate(UpgradeInfo upgradeInfo) {
        int localVerCode = ApkCheck.getInstalledApkVersionCode(BootActivity.this, getPackageName());
        if (localVerCode >= upgradeInfo.getCode()) {
            loadApp1();
        } else {
            ll_Update.setVisibility(View.VISIBLE);
            tv_UpdateMessage.setText(upgradeInfo.getInfo());
            bt_UpdateConfirm.requestFocus();
            mUpgradeInfo = upgradeInfo;
        }
    }

    public void loadApp1(){
        OkMaster.post(F.url.app_info)
                .enqueue(new StringListener() {
                    @Override
                    public void onSuccess(String s) throws IOException {
                        if(s == null){
                            return;
                        }
                        List<AppInfo> appInfos = new Gson().fromJson(s, new TypeToken<List<AppInfo>>(){}.getType()) ;
                        if(appInfos != null){
                            Observable.from(appInfos)
                                    .subscribe(new Action1<AppInfo>() {
                                        @Override
                                        public void call(AppInfo appInfo) {
                                            Logger.d(appInfo.toString());
                                            sqliteDao.insertOrUpdateData(appInfo);
                                        }
                                    });
                        }
                        startActivity(new Intent(BootActivity.this ,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(String e) {
                        startActivity(new Intent(BootActivity.this ,MainActivity.class));
                        finish();
                    }
                });
    }

    @OnClick(R.id.bt_UpdateConfirm)
    public void onClick() {
        if (mUpgradeInfo != null) {
            ll_Update.setVisibility(View.GONE);
            pb_UpdateProgress.setVisibility(View.VISIBLE);
            tv_Progress.setVisibility(View.VISIBLE);
            updateAppMarket(mUpgradeInfo);
        }
    }

    private void updateAppMarket(final UpgradeInfo upgradeInfo) {
        Logger.d(upgradeInfo.toString());
        if(downloadManager == null){
            downloadManager = new DownloadManager(BootActivity.this);
        }
        DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
        downloadFileInfo.setFileFullName(upgradeInfo.getName());
        downloadFileInfo.setFileDownloadUrl(upgradeInfo.getUrl());
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
                if(ApkCheck.isApkCanInstalled(BootActivity.this,F.path.market , upgradeInfo.getName())) {
                    ApkInstall.installApk(BootActivity.this, F.path.market, upgradeInfo.getName());
                }else{
                    SystemConfig.toastLong(BootActivity.this,getString(R.string.download_error));
                }
            }
        });

    }
}
