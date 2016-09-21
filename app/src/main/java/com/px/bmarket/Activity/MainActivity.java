package com.px.bmarket.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.px.bmarket.Fragment.ChatFragment;
import com.px.bmarket.Fragment.GameFragment;
import com.px.bmarket.Fragment.MusicFragment;
import com.px.bmarket.Fragment.RecommendFragment;
import com.px.bmarket.Fragment.ToolFragment;
import com.px.bmarket.Fragment.VideoFragment;
import com.px.bmarket.Presenter.MainActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.MD5;
import com.px.bmarket.Utils.SystemConfig;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PX on 2016/9/11.
 */
public class MainActivity extends BaseActivity<IMainActivity, MainActivityPresenter> implements IMainActivity {

    @BindView(R.id.lv_AppType)
    ListView lv_AppType;
    @BindView(R.id.tv_Marquee)
    MarqueeView tv_Marquee;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
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
    private List<ButtonInfo> mButtonInfos;
    private long backExitTime;

    private ChatFragment chatFragment;
    private GameFragment gameFragment;
    private MusicFragment musicFragment;
    private RecommendFragment recommendFragment;
    private ToolFragment toolFragment;
    private VideoFragment videoFragment;
    private final int RECOMMEND_POSITION = 0;
    private final int VIDEO_POSITION = 1;
    private final int GAMES_POSITION = 2;
    private final int CHAT_POSITION = 3;
    private final int MUSIC_POSITION = 4;
    private final int TOOL_POSITION = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        appTypeAdapter = new AppTypeAdapter(MainActivity.this);
        lv_AppType.setAdapter(appTypeAdapter);
        showFragment(RECOMMEND_POSITION);
        presenter.dispatch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lv_AppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
                showFragment(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv_AppType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
                showFragment(position);
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
            videoView.stopPlayback();
        }
    }

    private void showFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (position) {
            case RECOMMEND_POSITION:
                if(recommendFragment == null){
                    recommendFragment = new RecommendFragment();
                    fragmentTransaction.add(R.id.frameLayout ,recommendFragment);
                }else {
                    fragmentTransaction.show(recommendFragment);
                }
                break;
            case VIDEO_POSITION:
                if(videoFragment == null){
                    videoFragment = new VideoFragment();
                    fragmentTransaction.add(R.id.frameLayout ,videoFragment);
                }else {
                    fragmentTransaction.show(videoFragment);
                }
                break;
            case GAMES_POSITION:
                if(gameFragment == null){
                    gameFragment = new GameFragment();
                    fragmentTransaction.add(R.id.frameLayout ,gameFragment);
                }else {
                    fragmentTransaction.show(gameFragment);
                }
                break;
            case CHAT_POSITION:
                if(chatFragment == null){
                    chatFragment = new ChatFragment();
                    fragmentTransaction.add(R.id.frameLayout ,chatFragment);
                }else {
                    fragmentTransaction.show(chatFragment);
                }
                break;
            case MUSIC_POSITION:
                if(musicFragment == null){
                    musicFragment = new MusicFragment();
                    fragmentTransaction.add(R.id.frameLayout ,musicFragment);
                }else {
                    fragmentTransaction.show(musicFragment);
                }
                break;
            case TOOL_POSITION:
                if(toolFragment == null){
                    toolFragment = new ToolFragment();
                    fragmentTransaction.add(R.id.frameLayout ,toolFragment);
                }else {
                    fragmentTransaction.show(toolFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(recommendFragment != null){
            fragmentTransaction.hide(recommendFragment);
        }
        if(videoFragment != null){
            fragmentTransaction.hide(videoFragment);
        }
        if(gameFragment != null){
            fragmentTransaction.hide(gameFragment);
        }
        if(chatFragment != null){
            fragmentTransaction.hide(chatFragment);
        }
        if(musicFragment != null){
            fragmentTransaction.hide(musicFragment);
        }
        if(toolFragment != null){
            fragmentTransaction.hide(toolFragment);
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
                SystemConfig.openBrowserByUrl(MainActivity.this, rollImageInfos.get(position).getLinkUrl());
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
                "                         " +marqueeInfo.getText());
        tv_Marquee.setTextColor(Color.rgb(marqueeInfo.getColorR(),marqueeInfo.getColorG() ,marqueeInfo.getColorB()));
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

    private boolean isVideoCanPlay(String videoMD5){
        String localMD5 = MD5.getFileMD5(F.path.video ,"btvi3.mp4");
        if(localMD5.equals(videoMD5)){
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
