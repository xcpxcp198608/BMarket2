package com.px.bmarket.Data.MarqueeData;

import com.px.bmarket.Beans.MarqueeInfo;

/**
 * Created by PX on 2016/9/12.
 */
public interface IMarqueeData {
    void loadData(OnCompletedListener onCompletedListener);
    interface OnCompletedListener{
        void onCompleted(MarqueeInfo marqueeInfo);
        void onError (String e);
    }
}
