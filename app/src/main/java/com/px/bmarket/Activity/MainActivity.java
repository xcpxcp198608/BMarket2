package com.px.bmarket.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.px.bmarket.Adapter.AppTypeAdapter;
import com.px.bmarket.Adapter.AppsAdapter;
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
import com.px.bmarket.Presenter.MainActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.MD5;
import com.px.bmarket.Utils.SystemConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PX on 2016/9/11.
 */
public class MainActivity extends BaseActivity<IMainActivity, MainActivityPresenter> implements IMainActivity {

    @BindView(R.id.lv_AppType)
    ListView lv_AppType;
    @BindView(R.id.tv_Marquee)
    MarqueeView tv_Marquee;
    @BindView(R.id.gv_Apps)
    GridView gv_Apps;
    @BindView(R.id.rv_Image)
    RollPagerView rv_Image;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.bt_link1)
    Button bt_Link1;
    @BindView(R.id.bt_link2)
    Button bt_Link2;
    @BindView(R.id.bt_link3)
    Button bt_Link3;
    @BindView(R.id.bt_link4)
    Button bt_Link4;
    @BindView(R.id.bt_link5)
    Button bt_Link5;

    private AppTypeAdapter appTypeAdapter;
    private SQLiteDao sqLiteDao;
    private List<ButtonInfo> mButtonInfos;
    private long backExitTime;
    private AppsAdapter appsAdapter;
    private List<AppInfo> appInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sqLiteDao = SQLiteDao.getInstance(MainActivity.this);
        appTypeAdapter = new AppTypeAdapter(MainActivity.this);
        lv_AppType.setAdapter(appTypeAdapter);
        appInfoList = sqLiteDao.queryRecommend();
        appsAdapter = new AppsAdapter(MainActivity.this , appInfoList);
        gv_Apps.setAdapter(appsAdapter);
        presenter.dispatch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lv_AppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
                switch (position) {
                    case 0:
                        showRecommendApp();
                        break;
                    case 1:
                        showAppByType("movie");
                        break;
                    case 2:
                        showAppByType("ktv");
                        break;
                    case 3:
                        showAppByType("game");
                        break;
                    case 4:
                        showAppByType("chat");
                        break;
                    case 5:
                        showAppByType("music");
                        break;
                    case 6:
                        showAppByType("tool");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv_AppType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
                switch (position) {
                    case 0:
                        showRecommendApp();
                        break;
                    case 1:
                        showAppByType("movie");
                        break;
                    case 2:
                        showAppByType("ktv");
                        break;
                    case 3:
                        showAppByType("game");
                        break;
                    case 4:
                        showAppByType("chat");
                        break;
                    case 5:
                        showAppByType("music");
                        break;
                    case 6:
                        showAppByType("tool");
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView!=null){
            videoView.stopPlayback();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(videoView!=null){
            videoView.stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.pause();
            videoView.stopPlayback();
        }
    }

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    public void loadRollImage(final List<RollImageInfo> rollImageInfos) {
        if(rollImageInfos == null){
            return;
        }
        RollViewAdapter rollViewAdapter = new RollViewAdapter(rollImageInfos);
        rv_Image.setAdapter(rollViewAdapter);
        rv_Image.setHintView(null);
        rv_Image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Animator.zoomIn09_10(v);
                }
            }
        });
        rv_Image.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SystemConfig.openBrowserByUrl(MainActivity.this, rollImageInfos.get(position).getLink());
            }
        });
    }

    @Override
    public void loadMarquee(MarqueeInfo marqueeInfo) {
        if(marqueeInfo == null){
            return;
        }
        tv_Marquee.setText("                                                                         " +
                "                                                                                    " +
                "                                                                                    " +
                "                         " +marqueeInfo.getContent());
    }

    @Override
    public void loadButton(List<ButtonInfo> buttonInfos) {
        if(buttonInfos == null){
            return;
        }
        mButtonInfos = buttonInfos;
        bt_Link1.setText(buttonInfos.get(0).getText());
        bt_Link2.setText(buttonInfos.get(1).getText());
        bt_Link3.setText(buttonInfos.get(2).getText());
        bt_Link4.setText(buttonInfos.get(3).getText());
        bt_Link5.setText(buttonInfos.get(4).getText());
        bt_Link1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        bt_Link5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
    //显示推荐的APP
    private void showRecommendApp() {
        Observable.just("1").subscribeOn(Schedulers.io())
                .map(new Func1<String, List<AppInfo>>() {
                    @Override
                    public List<AppInfo> call(String s) {
                        return sqLiteDao.queryRecommend();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AppInfo>>() {
                    @Override
                    public void call(final List<AppInfo> appInfoList) {
                        appsAdapter.refresh(appInfoList);
                        gv_Apps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Animator.zoomIn09_10(view);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        gv_Apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                AppInfo appInfo = appInfoList.get(position);
                                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                                intent.putExtra("appInfo", appInfo);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }
    //根据选择的类型显示APP
    private void showAppByType(final String type) {
        Observable.just(type).subscribeOn(Schedulers.io())
                .map(new Func1<String, List<AppInfo>>() {
                    @Override
                    public List<AppInfo> call(String s) {
                        return sqLiteDao.queryByType(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AppInfo>>() {
                    @Override
                    public void call(final List<AppInfo> appInfoList) {
                        appsAdapter.refresh(appInfoList);
                        gv_Apps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Animator.zoomIn09_10(view);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        gv_Apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                AppInfo appInfo = appInfoList.get(position);
                                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                                intent.putExtra("appInfo", appInfo);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private boolean isVideoCanPlay(String videoMD5){
        String localMD5 = MD5.getFileMD5(F.path.video ,"btvi3.mp4");
        if(localMD5.equalsIgnoreCase(videoMD5)){
            return true;
        }else{
            return false;
        }
    }
    //播放影片
    private void playVideo(final String videoMD5) {
        if(isVideoCanPlay(videoMD5)){
            videoView.setVideoPath(F.path.video+"btvi3.mp4");
        }else {
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.btvi3));
        }
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isVideoCanPlay(videoMD5)){
                    videoView.setVideoPath(F.path.video+"btvi3.mp4");
                }else {
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.btvi3));
                }
                videoView.start();
            }
        });
    }

    @OnClick({R.id.bt_link1, R.id.bt_link2, R.id.bt_link3, R.id.bt_link4, R.id.bt_link5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_link1:
                SystemConfig.openBrowserByUrl(MainActivity.this ,mButtonInfos.get(0).getUrl());
                break;
            case R.id.bt_link2:
                SystemConfig.openBrowserByUrl(MainActivity.this ,mButtonInfos.get(1).getUrl());
                break;
            case R.id.bt_link3:
                SystemConfig.openBrowserByUrl(MainActivity.this ,mButtonInfos.get(2).getUrl());
                break;
            case R.id.bt_link4:
                SystemConfig.openBrowserByUrl(MainActivity.this ,mButtonInfos.get(3).getUrl());
                break;
            case R.id.bt_link5:
                SystemConfig.openBrowserByUrl(MainActivity.this ,mButtonInfos.get(4).getUrl());
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - backExitTime) > 2000) {
                Toast.makeText(MainActivity.this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show();
                backExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
