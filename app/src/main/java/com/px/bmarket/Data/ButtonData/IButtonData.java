package com.px.bmarket.Data.ButtonData;

import com.px.bmarket.Beans.ButtonInfo;

import java.util.List;

/**
 * Created by PX on 2016/9/12.
 */
public interface IButtonData {
    void loadData(OnCompletedListener onCompletedListener);
    interface OnCompletedListener{
        void onCompleted(List<ButtonInfo> list);
        void onError(String e);
    }
}
