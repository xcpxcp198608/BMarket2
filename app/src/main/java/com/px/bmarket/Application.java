package com.px.bmarket;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.FileDownload.DownloadManager;
import com.px.bmarket.Utils.FileDownload.OnDownloadListener;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by PX on 2016/9/11.
 */
public class Application extends android.app.Application {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static RequestQueue requestQueue;
    private boolean isDownloading = false;
    private DownloadManager downloadManager;
    private static String videoMD5;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = this.getSharedPreferences(F.sp.video , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadVideoInfo();
    }

    public static String getVideoMd5(){
        return videoMD5;
    }

    public static RequestQueue getVolleyRequest(){
        return requestQueue;
    }

    private void loadVideoInfo(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(F.url.video_info, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                VideoInfo videoInfo = new VideoInfo();
                try {
                    videoInfo.setVideoFileName(jsonObject.getString("videoFileName"));
                    videoInfo.setVideoUrl(jsonObject.getString("videoUrl"));
                    videoInfo.setVersion(jsonObject.getInt("version"));
                    videoInfo.setMd5(jsonObject.getString("md5"));
                    videoMD5 = videoInfo.getMd5();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadVideo(videoInfo);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        jsonObjectRequest.setTag("VideoInfo");
        Application.getVolleyRequest().add(jsonObjectRequest);
    }

    public void loadVideo(VideoInfo videoInfo){
        Logger.d(videoInfo.toString());
        int localVersion = sharedPreferences.getInt("version" ,0);
        isDownloading = sharedPreferences.getBoolean("isDownloading" ,false);
        if(!ApkCheck.isFileExists(F.path.video ,"btvi3.mp4") && !isDownloading){
            Logger.d("----video is not exists");
            downloadVideo(videoInfo);
        }else if(videoInfo.getVersion() > localVersion && !isDownloading){
            Logger.d("----video version need update");
            downloadVideo(videoInfo);
        }else if(!isFileIntact(videoInfo.getMd5()) && !isDownloading){
            Logger.d("----video is not intact");
            downloadVideo(videoInfo);
        }else{
            Logger.d("----video no need update");
        }
    }

    public void downloadVideo(final VideoInfo videoInfo){
        if(downloadManager==null){
            downloadManager = DownloadManager.getInstance(getApplicationContext());
        }
        downloadManager.startDownload(videoInfo.getVideoFileName(),videoInfo.getVideoUrl(),F.path.video);
        downloadManager.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onStart(int progress, boolean isStart) {
                editor.clear();
                editor.putBoolean("isDownloading" ,true);
                editor.commit();
            }

            @Override
            public void onProgressChange(int progress) {

            }

            @Override
            public void onPause(int progress) {

            }

            @Override
            public void onCompleted(int progress) {
                Logger.d("video download finish");
                editor.clear();
                editor.putInt("version" ,videoInfo.getVersion());
                editor.putBoolean("isDownloading" ,false);
                editor.commit();
            }
        });
    }

    public boolean isFileIntact(String md5){
        String localMD5 = MD5.getFileMD5(F.path.video ,"btvi3.mp4");
        if(localMD5.equals(md5)){
            return true;
        }else{
            return false;
        }
    }

}
