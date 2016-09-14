package com.px.bmarket.Data.AppMarketData;

import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.F;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PX on 2016/9/11.
 */
public class AppMarketData implements IAppMarketData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);

        new Retrofit.Builder()
                .baseUrl(F.url.base_url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IAppMarketService.class)
                .getData()
                .enqueue(new Callback<AppMarketInfo>() {
                    @Override
                    public void onResponse(Call<AppMarketInfo> call, Response<AppMarketInfo> response) {
                        if(response.body() != null){
                            onCompletedListener.onCompleted(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AppMarketInfo> call, Throwable t) {
                        onCompletedListener.onError(t.getMessage());
                    }
                });
    }
}
