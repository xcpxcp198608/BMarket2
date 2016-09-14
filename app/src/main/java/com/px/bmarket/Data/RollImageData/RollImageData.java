package com.px.bmarket.Data.RollImageData;

import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.F;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PX on 2016/9/12.
 */
public class RollImageData implements IRollImageData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        new Retrofit.Builder().baseUrl(F.url.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IRollImageService.class)
                .getData()
                .enqueue(new Callback<List<RollImageInfo>>() {
                    @Override
                    public void onResponse(Call<List<RollImageInfo>> call, Response<List<RollImageInfo>> response) {
                        if(response!=null){
                            onCompletedListener.onCompleted(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RollImageInfo>> call, Throwable t) {
                        onCompletedListener.onError(t.getMessage());
                    }
                });
    }
}