package com.px.bmarket.Data.VideoData;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.px.bmarket.Application;
import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.F;
import com.px.bmarket.Utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PX on 2016/9/17.
 */
public class VideoData implements IVideoData {
    @Override
    public void loadData(final OnCompletedListener onCompletedListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(F.url.video_info, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                VideoInfo videoInfo= new VideoInfo();
                try {
                    videoInfo.setVideoFileName(jsonObject.getString("videoFileName"));
                    videoInfo.setVideoUrl(jsonObject.getString("videoUrl"));
                    videoInfo.setVersion(jsonObject.getInt("version"));
                    videoInfo.setMd5(jsonObject.getString("md5"));
//                    Logger.d(videoInfo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onCompletedListener.onCompleted(videoInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onCompletedListener.onError(volleyError.getMessage());
            }
        });
        jsonObjectRequest.setTag("VideoInfo");
        Application.getVolleyRequest().add(jsonObjectRequest);
    }
}
