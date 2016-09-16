package com.px.bmarket.Data.AppMarketData;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.AppMarketInfo;
import com.px.bmarket.F;
import com.px.bmarket.Utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(F.url.app_market, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                AppMarketInfo appMarketInfo = new AppMarketInfo();
                try {
                    appMarketInfo.setApkName(jsonObject.getString("apkName"));
                    appMarketInfo.setApkFileName(jsonObject.getString("apkFileName"));
                    appMarketInfo.setApkFileDownloadUrl(jsonObject.getString("apkFileDownloadUrl"));
                    appMarketInfo.setApkPackageName(jsonObject.getString("apkPackageName"));
                    appMarketInfo.setApkUpdateInfo(jsonObject.getString("apkUpdateInfo"));
                    appMarketInfo.setApkVersionCode(jsonObject.getInt("apkVersionCode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onCompletedListener.onCompleted(appMarketInfo);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onCompletedListener.onError(volleyError.getMessage());
            }
        });
        jsonObjectRequest.setTag("AppMarketInfo");
        Application.getVolleyRequest().add(jsonObjectRequest);
    }
}
