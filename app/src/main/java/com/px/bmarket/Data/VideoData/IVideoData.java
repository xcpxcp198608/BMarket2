package com.px.bmarket.Data.VideoData;

import com.px.bmarket.Beans.VideoInfo;

/**
 * Created by PX on 2016/9/14.
 */
public interface IVideoData {
    void loadData(OnCompletedListener completedListener);
    interface OnCompletedListener{
        void onCompleted(VideoInfo videoInfo);
        void onError (String e);
    }
}
