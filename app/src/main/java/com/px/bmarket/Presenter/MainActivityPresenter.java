package com.px.bmarket.Presenter;

import com.px.bmarket.Activity.IMainActivity;
import com.px.bmarket.Data.ButtonData.IButtonData;
import com.px.bmarket.Data.MarqueeData.IMarqueeData;
import com.px.bmarket.Data.RollImageData.IRollImageData;
import com.px.bmarket.Data.VideoData.IVideoData;

/**
 * Created by PX on 2016/9/12.
 */
public class MainActivityPresenter extends BasePresenter<IMainActivity> {
    private IMainActivity iMainActivity;
    private IRollImageData iRollImageData;
    private IVideoData iVideoData;
    private IMarqueeData iMarqueeData;
    private IButtonData iButtonData;
    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;

    }

    public void dispatch(){

    }
}
