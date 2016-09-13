package com.px.bmarket.Data.AppMarketData;

import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.F;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PX on 2016/9/11.
 */
public interface IAppMarketService {
    @GET(F.url.app_market)
    Call<AppMarketInfo> getData ();
}
