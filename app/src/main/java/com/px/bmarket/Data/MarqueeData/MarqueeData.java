package com.px.bmarket.Data.MarqueeData;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.F;

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
 * Created by PX on 2016/9/12.
 */
public class MarqueeData implements IMarqueeData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(F.url.marquee_info, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                MarqueeInfo marqueeInfo = new MarqueeInfo();
                try {
                    marqueeInfo.setText(jsonObject.getString("text"));
                    marqueeInfo.setColorR(jsonObject.getInt("colorR"));
                    marqueeInfo.setColorG(jsonObject.getInt("colorG"));
                    marqueeInfo.setColorB(jsonObject.getInt("colorB"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onCompletedListener.onCompleted(marqueeInfo);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onCompletedListener.onError(volleyError.getMessage());
            }
        });
        jsonObjectRequest.setTag("MarqueeInfo");
        Application.getVolleyRequest().add(jsonObjectRequest);
    }
}
