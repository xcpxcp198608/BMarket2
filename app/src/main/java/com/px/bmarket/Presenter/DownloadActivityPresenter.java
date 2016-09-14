package com.px.bmarket.Presenter;

import com.px.bmarket.Activity.BaseActivity;
import com.px.bmarket.Activity.IDownloadActivity;
import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.Data.ButtonData.ButtonData;
import com.px.bmarket.Data.ButtonData.IButtonData;
import com.px.bmarket.Data.MarqueeData.IMarqueeData;
import com.px.bmarket.Data.MarqueeData.MarqueeData;
import com.px.bmarket.Data.RollImageData.IRollImageData;
import com.px.bmarket.Data.RollImageData.RollImageData;
import com.px.bmarket.Utils.Logger;

import java.util.List;

/**
 * Created by PX on 2016/9/13.
 */
public class DownloadActivityPresenter extends BasePresenter<IDownloadActivity> {
    private IDownloadActivity iDownloadActivity;
    private IRollImageData iRollImageData;
    private IButtonData iButtonData;
    private IMarqueeData iMarqueeData;

    public DownloadActivityPresenter(IDownloadActivity iDownloadActivity) {
        this.iDownloadActivity = iDownloadActivity;
        iRollImageData = new RollImageData();
        iMarqueeData = new MarqueeData();
        iButtonData = new ButtonData();
    }

    public void dispatch(){
        if(iRollImageData != null){
            iRollImageData.loadData(new IRollImageData.OnCompletedListener() {
                @Override
                public void onCompleted(List<RollImageInfo> list) {
                    iDownloadActivity.loadRollImage(list);
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
                    iDownloadActivity.loadMarquee(marqueeInfo);
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
                    iDownloadActivity.loadButton(list);
                }

                @Override
                public void onError(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
