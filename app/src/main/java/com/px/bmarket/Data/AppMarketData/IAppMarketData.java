package com.px.bmarket.Data.AppMarketData;

import com.px.bmarket.Beans.AppMarketInfo;

/**
 * Created by PX on 2016/9/11.
 */
public interface IAppMarketData {
    void loadData(OnCompletedListener onCompletedListener);
    interface OnCompletedListener{
        void onCompleted(AppMarketInfo appMarketInfo);
        void onError(String error);
    }
}
