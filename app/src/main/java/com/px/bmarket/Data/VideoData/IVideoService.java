package com.px.bmarket.Data.VideoData;

import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.F;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by PX on 2016/9/12.
 */
public interface IVideoService {
    @GET(F.url.video_info)
    Call<VideoInfo> getData();
}
