package com.px.bmarket.Data.ButtonData;

import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.F;

import java.util.List;
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
public class ButtonData implements IButtonData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(30,TimeUnit.SECONDS);
        new Retrofit.Builder().baseUrl(F.url.base_url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IButtonService.class)
                .getData()
                .enqueue(new Callback<List<ButtonInfo>>() {
                    @Override
                    public void onResponse(Call<List<ButtonInfo>> call, Response<List<ButtonInfo>> response) {
                        onCompletedListener.onCompleted(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ButtonInfo>> call, Throwable t) {
                        onCompletedListener.onError(t.getMessage());
                    }
                });
    }
}
