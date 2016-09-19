package com.px.bmarket.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.px.bmarket.Adapter.RollViewAdapter;
import com.px.bmarket.Animator.Animator;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.CustomView.MarqueeView;
import com.px.bmarket.F;
import com.px.bmarket.FileDownload.DownloadFileInfo;
import com.px.bmarket.FileDownload.DownloadManager;
import com.px.bmarket.FileDownload.DownloadStatusListener;
import com.px.bmarket.Presenter.DownloadActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.ApkInstall;
import com.px.bmarket.Utils.ApkLaunch;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.MD5;
import com.px.bmarket.Utils.SystemConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PX on 2016/9/11.
 */
public class DownloadActivity extends BaseActivity<IDownloadActivity, DownloadActivityPresenter> implements IDownloadActivity {
    @BindView(R.id.bt_link1_1)
    Button bt_Link11;
    @BindView(R.id.bt_link2_1)
    Button bt_Link21;
    @BindView(R.id.bt_link3_1)
    Button bt_Link31;
    @BindView(R.id.bt_link4_1)
    Button bt_Link41;
    @BindView(R.id.bt_link5_1)
    Button bt_Link51;
    @BindView(R.id.iv_AppIcon)
    ImageView iv_AppIcon;
    @BindView(R.id.tv_AppName)
    TextView tv_AppName;
    @BindView(R.id.tv_AppType)
    TextView tv_AppType;
    @BindView(R.id.tv_AppSize)
    TextView tv_AppSize;
    @BindView(R.id.tv_AppVersion)
    TextView tv_AppVersion;
    @BindView(R.id.tv_AppLanuage)
    TextView tv_AppLanuage;
    @BindView(R.id.bt_Download)
    Button bt_Download;
    @BindView(R.id.tv_Progress_1)
    TextView tv_Progress1;
    @BindView(R.id.pb_DownloadProgress)
    ProgressBar pb_DownloadProgress;
    @BindView(R.id.rv_Image_1)
    RollPagerView rv_Image1;
    @BindView(R.id.videoView_1)
    VideoView videoView1;
    @BindView(R.id.tv_AppSummary)
    TextView tv_AppSummary;
    @BindView(R.id.tv_Marquee_1)
    MarqueeView tv_Marquee1;

    private AppInfo appInfo;
    private List<ButtonInfo> mButtonInfos;
    private DownloadManager downloadManager;
    private boolean isDownloading =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        appInfo = getIntent().getParcelableExtra("appInfo");
        if(appInfo!= null){
            Picasso.with(DownloadActivity.this).load(appInfo.getApkIconUrl()).placeholder(R.drawable.loading).error(R.drawable.error)
                    .into(iv_AppIcon);
            tv_AppName.setText(appInfo.getApkName());
            tv_AppType.setText(getString(R.string.text_type)+appInfo.getApkType());
            tv_AppSize.setText(getString(R.string.text_size)+appInfo.getApkSize()+"Mb");
            tv_AppVersion.setText(getString(R.string.text_version)+appInfo.getApkVersion());
            tv_AppLanuage.setText(getString(R.string.text_language)+appInfo.getApkLanguage());
            tv_AppSummary.setText(appInfo.getApkSummary());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.dispatch();
        bt_Download.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(videoView1!= null) {
            videoView1.pause();
            videoView1.stopPlayback();
        }
        if(downloadManager!= null){
            downloadManager.pauseDownload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView1!= null) {
            videoView1.pause();
            videoView1.stopPlayback();
        }
        if(downloadManager!= null){
            downloadManager.pauseDownload();
        }
    }

    @Override
    protected DownloadActivityPresenter createPresenter() {
        return new DownloadActivityPresenter(this);
    }

    @Override
    public void loadRollImage(final List<RollImageInfo> rollImageInfos) {
        if(rollImageInfos == null){
            return;
        }
        RollViewAdapter rollViewAdapter = new RollViewAdapter(rollImageInfos);
        rv_Image1.setAdapter(rollViewAdapter);
        rv_Image1.setHintView(null);
        rv_Image1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Animator.zoomIn09_10(v);
                }
            }
        });
        rv_Image1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SystemConfig.openBrowserByUrl(DownloadActivity.this, rollImageInfos.get(position).getLinkUrl());
            }
        });
    }

    @Override
    public void loadMarquee(MarqueeInfo marqueeInfo) {
        if(marqueeInfo == null){
            return;
        }
        tv_Marquee1.setText("                                                                         " +
                "                                                                                    " +
                "                                                                                    " +
                "                         " +marqueeInfo.getText());
        tv_Marquee1.setTextColor(Color.rgb(marqueeInfo.getColorR(),marqueeInfo.getColorG() ,marqueeInfo.getColorB()));
    }

    @Override
    public void loadButton(List<ButtonInfo> buttonInfos) {
        if(buttonInfos == null){
            return;
        }
        mButtonInfos = buttonInfos;
        bt_Link11.setText(buttonInfos.get(0).getText());
        bt_Link21.setText(buttonInfos.get(1).getText());
        bt_Link31.setText(buttonInfos.get(2).getText());
        bt_Link41.setText(buttonInfos.get(3).getText());
        bt_Link51.setText(buttonInfos.get(4).getText());
        bt_Link11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link21.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link31.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link41.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link51.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
    }

    @Override
    public void loadVideo(VideoInfo videoInfo) {
        playVideo(videoInfo.getMd5());
    }

    @OnClick({R.id.bt_link1_1, R.id.bt_link2_1, R.id.bt_link3_1, R.id.bt_link4_1, R.id.bt_link5_1, R.id.bt_Download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_link1_1:
                SystemConfig.openBrowserByUrl(DownloadActivity.this ,mButtonInfos.get(0).getUrl());
                break;
            case R.id.bt_link2_1:
                SystemConfig.openBrowserByUrl(DownloadActivity.this ,mButtonInfos.get(1).getUrl());
                break;
            case R.id.bt_link3_1:
                SystemConfig.openBrowserByUrl(DownloadActivity.this ,mButtonInfos.get(2).getUrl());
                break;
            case R.id.bt_link4_1:
                SystemConfig.openBrowserByUrl(DownloadActivity.this ,mButtonInfos.get(3).getUrl());
                break;
            case R.id.bt_link5_1:
                SystemConfig.openBrowserByUrl(DownloadActivity.this ,mButtonInfos.get(4).getUrl());
                break;
            case R.id.bt_Download:
                String bt_Text = bt_Download.getText().toString().trim();
                if(getString(R.string.text_download).equals(bt_Text)){
                    downloadApp();
                }else if(getString(R.string.text_downloading).equals(bt_Text)){

                }else if(getString(R.string.text_install).equals(bt_Text)){
                    if(ApkCheck.isApkCanInstalled(DownloadActivity.this,F.path.apps,appInfo.getApkFileName())){
                        ApkInstall.installApk(DownloadActivity.this ,F.path.apps ,appInfo.getApkFileName());
                    }
                }else if(getString(R.string.text_launch).equals(bt_Text)){
                    ApkLaunch.launchApkByPackageName(DownloadActivity.this,appInfo.getApkPackageName());
                }
                break;
        }
    }

    private void downloadApp(){
        if(downloadManager == null){
            downloadManager = new DownloadManager(DownloadActivity.this);
            DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
            downloadFileInfo.setFileFullName(appInfo.getApkFileName());
            downloadFileInfo.setFileDownloadUrl(appInfo.getApkDownloadUrl());
            downloadManager.startDownload(downloadFileInfo , F.path.apps);
            downloadManager.setDownloadStatusListener(new DownloadStatusListener() {
                @Override
                public void startDownload(boolean isStart, long fileLength) {
                    isDownloading = true;
                    bt_Download.setText(getString(R.string.text_downloading));
                    bt_Download.setBackgroundResource(R.drawable.button_red_normal);
                    tv_Progress1.setVisibility(View.VISIBLE);
                    pb_DownloadProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void pauseDownload(boolean isPauseDownload, int progress) {
                    isDownloading = false;
                }

                @Override
                public void downloadProgressChanged(boolean isDownloading, int progress) {
                    tv_Progress1.setText(progress+"%");
                    pb_DownloadProgress.setProgress(progress);
                }

                @Override
                public void completedDownload(boolean isCompleted, int progress) {
                    isDownloading = false;
                    bt_Download.setText(getString(R.string.text_install));
                    bt_Download.setBackgroundResource(R.drawable.button_blue);
                    tv_Progress1.setVisibility(View.GONE);
                    pb_DownloadProgress.setVisibility(View.GONE);
                    if(ApkCheck.isApkCanInstalled(DownloadActivity.this,F.path.apps,appInfo.getApkFileName())){
                        ApkInstall.installApk(DownloadActivity.this ,F.path.apps ,appInfo.getApkFileName());
                    }
                }
            });
        }
    }

    private void setButtonStatus(){
        if(ApkCheck.isApkInstalled(DownloadActivity.this ,appInfo.getApkPackageName())){
            bt_Download.setText(getString(R.string.text_launch));
            bt_Download.setBackgroundResource(R.drawable.button_green);
        }else if(ApkCheck.isFileExists(F.path.apps ,appInfo.getApkFileName())
                && ApkCheck.isApkCanInstalled(DownloadActivity.this ,F.path.apps ,appInfo.getApkFileName())){
            bt_Download.setText(getString(R.string.text_install));
            bt_Download.setBackgroundResource(R.drawable.button_blue);
        }else if(isDownloading){
            bt_Download.setText(getString(R.string.text_downloading));
            bt_Download.setBackgroundResource(R.drawable.button_red_normal);
        }else {
            bt_Download.setText(getString(R.string.text_download));
            bt_Download.setBackgroundResource(R.drawable.button_blue);
        }
    }

    private boolean isVideoCanPlay(String videoMD5){
        String localMD5 = MD5.getFileMD5(F.path.video ,"btvi3.mp4");
        if(localMD5.equals(videoMD5)){
            return true;
        }else{
            return false;
        }
    }

    private void playVideo(final String videoMD5) {
        if(isVideoCanPlay(videoMD5)){
            videoView1.setVideoPath(F.path.video+"btvi3.mp4");
        }else {
            videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.btvi3));
        }
        videoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView1.start();
            }
        });
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isVideoCanPlay(videoMD5)){
                    videoView1.setVideoPath(F.path.video+"btvi3.mp4");
                }else {
                    videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.btvi3));
                }
                videoView1.start();
            }
        });
    }
}
