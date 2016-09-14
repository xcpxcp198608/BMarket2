package com.px.bmarket.Data.MarqueeData;

import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.F;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PX on 2016/9/12.
 */
public interface IMarqueeService {
    @GET(F.url.marquee_info)
    Call<MarqueeInfo> getData();
}
