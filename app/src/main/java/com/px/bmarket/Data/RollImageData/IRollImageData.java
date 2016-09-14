package com.px.bmarket.Data.RollImageData;

import com.px.bmarket.Beans.RollImageInfo;

import java.util.List;

/**
 * Created by PX on 2016/9/11.
 */
public interface IRollImageData {
    void loadData(OnCompletedListener onCompletedListener);
    interface OnCompletedListener{
        void onCompleted(List<RollImageInfo> list);
        void onError(String e);
    }
}
