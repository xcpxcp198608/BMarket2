package com.px.bmarket.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.px.bmarket.Adapter.AppTypeAdapter;
import com.px.bmarket.Adapter.AppsAdapter;
import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.CustomView.MarqueeView;
import com.px.bmarket.Presenter.MainActivityPresenter;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private AppTypeAdapter appTypeAdapter;
    private AppsAdapter appsAdapter;
    private SQLiteDao sqLiteDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sqLiteDao = SQLiteDao.getInstance(MainActivity.this);
        appTypeAdapter = new AppTypeAdapter(MainActivity.this);
        lv_AppType.setAdapter(appTypeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.dispatch();
        showRecommendApp();
        lv_AppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showRecommendApp();
                        break;
                    case 1:
                        showAppByType("video");
                        break;
                    case 2:
                        showAppByType("game");
                        break;
                    case 3:
                        showAppByType("chat");
                        break;
                    case 4:
                        showAppByType("music");
                        break;
                    case 5:
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
                switch (position){
                    case 0:
                        showRecommendApp();
                        break;
                    case 1:
                        showAppByType("video");
                        break;
                    case 2:
                        showAppByType("game");
                        break;
                    case 3:
                        showAppByType("chat");
                        break;
                    case 4:
                        showAppByType("music");
                        break;
                    case 5:
                        showAppByType("tool");
                        break;
                }
            }
        });
    }

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    public void loadRollImage(List<RollImageInfo> rollImageInfos) {

    }

    @Override
    public void loadVideo(VideoInfo videoInfo) {

    }

    @Override
    public void loadMarquee(MarqueeInfo marqueeInfo) {

    }

    @Override
    public void loadButton(List<ButtonInfo> buttonInfos) {

    }

    private void showRecommendApp(){
        List<AppInfo> list = new ArrayList<>();
        list = sqLiteDao.queryRecommend();
        AppsAdapter appsAdapter = new AppsAdapter(MainActivity.this , list);
        gv_Apps.setAdapter(appsAdapter);
    }

    private void showAppByType(String type){
        List<AppInfo> list = new ArrayList<>();
        list = sqLiteDao.queryByType(type);
        AppsAdapter appsAdapter = new AppsAdapter(MainActivity.this , list);
        gv_Apps.setAdapter(appsAdapter);
    }
}
