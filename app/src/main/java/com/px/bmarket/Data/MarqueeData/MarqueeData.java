package com.px.bmarket.Data.MarqueeData;

import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.F;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PX on 2016/9/12.
 */
public class MarqueeData implements IMarqueeData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);

        new Retrofit.Builder().baseUrl(F.url.base_url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IMarqueeService.class)
                .getData()
                .enqueue(new Callback<MarqueeInfo>() {
                    @Override
                    public void onResponse(Call<MarqueeInfo> call, Response<MarqueeInfo> response) {
                        onCompletedListener.onCompleted(response.body());
                    }

                    @Override
                    public void onFailure(Call<MarqueeInfo> call, Throwable t) {
                        onCompletedListener.onError(t.getMessage());
                    }
                });
    }
}
