package com.px.bmarket.Data.ButtonData;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.ButtonInfo;
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
public class ButtonData implements IButtonData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(F.url.button_info, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if(jsonArray.length()>0){
                    try {
                        List<ButtonInfo> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length() ; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ButtonInfo buttonInfo = new ButtonInfo();
                                buttonInfo.setText(jsonObject.getString("text"));
                                buttonInfo.setUrl(jsonObject.getString("url"));
                                list.add(buttonInfo);
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
        jsonArrayRequest.setTag("ButtonInfo");
        Application.getVolleyRequest().add(jsonArrayRequest);
    }
}
