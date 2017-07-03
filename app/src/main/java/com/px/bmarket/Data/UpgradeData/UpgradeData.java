package com.px.bmarket.Data.UpgradeData;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.UpgradeInfo;
import com.px.bmarket.F;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PX on 2016/9/11.
 */
public class UpgradeData implements IUpgradeData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(F.url.app_market, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                UpgradeInfo upgradeInfo = new UpgradeInfo();
                try {
                    upgradeInfo.setId(jsonObject.getInt("id"));
                    upgradeInfo.setName(jsonObject.getString("name"));
                    upgradeInfo.setUrl(jsonObject.getString("url"));
                    upgradeInfo.setCode(jsonObject.getInt("code"));
                    upgradeInfo.setVersion(jsonObject.getString("version"));
                    upgradeInfo.setInfo(jsonObject.getString("info"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onCompletedListener.onCompleted(upgradeInfo);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onCompletedListener.onError(volleyError.getMessage());
            }
        });
        jsonObjectRequest.setTag("UpgradeInfo");
        Application.getVolleyRequest().add(jsonObjectRequest);
    }
}
