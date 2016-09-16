package com.px.bmarket.Data.RollImageData;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.F;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
public class RollImageData implements IRollImageData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(F.url.roll_view_info, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if(jsonArray.length()>0){
                    try {
                        List<RollImageInfo> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            RollImageInfo r = new RollImageInfo();
                            r.setImageUrl(j.getString("imageUrl"));
                            r.setLinkUrl(j.getString("linkUrl"));
                            list.add(r);
                        }
                        onCompletedListener.onCompleted(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onCompletedListener.onError(volleyError.getMessage());
            }
        });
        jsonArrayRequest.setTag("RollImageInfo");
        Application.getVolleyRequest().add(jsonArrayRequest);
    }
}
