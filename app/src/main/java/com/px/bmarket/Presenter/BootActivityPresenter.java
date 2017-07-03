package com.px.bmarket.Presenter;

import com.px.bmarket.Activity.IBootActivity;
import com.px.bmarket.Beans.UpgradeInfo;
import com.px.bmarket.Data.UpgradeData.UpgradeData;
import com.px.bmarket.Data.UpgradeData.IUpgradeData;
import com.px.bmarket.Utils.Logger;

/**
 * Created by PX on 2016/9/11.
 */
public class BootActivityPresenter extends BasePresenter<IBootActivity> {
    private IBootActivity iBootActivity;
    private IUpgradeData iUpgradeData;

    public BootActivityPresenter(IBootActivity iBootActivity) {
        this.iBootActivity = iBootActivity;
        iUpgradeData = new UpgradeData();
    }

    public void checkUpdate(){
        if(iUpgradeData != null){
            iUpgradeData.loadData(new IUpgradeData.OnCompletedListener() {
                @Override
                public void onCompleted(UpgradeInfo upgradeInfo) {
                    iBootActivity.checkUpdate(upgradeInfo);
                }

                @Override
                public void onError(String error) {
                    Logger.d(error);
                }
            });
        }

    }
}
