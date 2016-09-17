package com.px.bmarket.Presenter;

import android.content.Context;

import com.px.bmarket.Activity.IMainActivity;
import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.Data.ButtonData.ButtonData;
import com.px.bmarket.Data.ButtonData.IButtonData;
import com.px.bmarket.Data.MarqueeData.IMarqueeData;
import com.px.bmarket.Data.MarqueeData.MarqueeData;
import com.px.bmarket.Data.RollImageData.IRollImageData;
import com.px.bmarket.Data.RollImageData.RollImageData;
import com.px.bmarket.Data.VideoData.IVideoData;
import com.px.bmarket.Data.VideoData.VideoData;
import com.px.bmarket.Utils.Logger;

import java.util.List;

/**
 * Created by PX on 2016/9/12.
 */
public class MainActivityPresenter extends BasePresenter<IMainActivity> {
    private IMainActivity iMainActivity;
    private IRollImageData iRollImageData;
    private IMarqueeData iMarqueeData;
    private IButtonData iButtonData;
    private IVideoData iVideoData;
    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        iRollImageData = new RollImageData();
        iMarqueeData = new MarqueeData();
        iButtonData = new ButtonData();
        iVideoData = new VideoData();
    }

    public void dispatch(){
        if(iRollImageData != null){
            iRollImageData.loadData(new IRollImageData.OnCompletedListener() {
                @Override
                public void onCompleted(List<RollImageInfo> list) {
                    iMainActivity.loadRollImage(list);
                }

                @Override
                public void onError(String e) {
                    Logger.d(e);
                }
            });
        }

        if(iMarqueeData != null){
            iMarqueeData.loadData(new IMarqueeData.OnCompletedListener() {
                @Override
                public void onCompleted(MarqueeInfo marqueeInfo) {
                    iMainActivity.loadMarquee(marqueeInfo);
                }

                @Override
                public void onError(String e) {
                    Logger.d(e);
                }
            });
        }

        if(iButtonData != null){
            iButtonData.loadData(new IButtonData.OnCompletedListener() {
                @Override
                public void onCompleted(List<ButtonInfo> list) {
                    iMainActivity.loadButton(list);
                }

                @Override
                public void onError(String e) {
                    Logger.d(e);
                }
            });
        }
        if(iVideoData != null){
            iVideoData.loadData(new IVideoData.OnCompletedListener() {
                @Override
                public void onCompleted(VideoInfo videoInfo) {
                    iMainActivity.loadVideo(videoInfo);
                }

                @Override
                public void onError(String e) {

                }
            });
        }
    }
}
