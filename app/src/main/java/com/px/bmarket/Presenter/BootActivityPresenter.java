package com.px.bmarket.Presenter;

import com.px.bmarket.Activity.IBootActivity;
import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.Data.AppMarketData.AppMarketData;
import com.px.bmarket.Data.AppMarketData.IAppMarketData;
import com.px.bmarket.Utils.Logger;

/**
 * Created by PX on 2016/9/11.
 */
public class BootActivityPresenter extends BasePresenter<IBootActivity> {
    private IBootActivity iBootActivity;
    private IAppMarketData iAppMarketData;

    public BootActivityPresenter(IBootActivity iBootActivity) {
        this.iBootActivity = iBootActivity;
        iAppMarketData = new AppMarketData();
    }

    public void dispatch(){
        if(iAppMarketData != null){
            iAppMarketData.loadData(new IAppMarketData.OnCompletedListener() {
                @Override
                public void onCompleted(AppMarketInfo appMarketInfo) {
                    iBootActivity.checkUpdate(appMarketInfo);
                }

                @Override
                public void onError(String error) {
                    Logger.d(error);
                }
            });
        }

    }
}
