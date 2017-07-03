package com.px.bmarket.Data.UpgradeData;

import com.px.bmarket.Beans.UpgradeInfo;

/**
 * Created by PX on 2016/9/11.
 */
public interface IUpgradeData {
    void loadData(OnCompletedListener onCompletedListener);
    interface OnCompletedListener{
        void onCompleted(UpgradeInfo upgradeInfo);
        void onError(String error);
    }
}
