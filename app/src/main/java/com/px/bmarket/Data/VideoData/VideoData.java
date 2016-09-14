package com.px.bmarket.Data.VideoData;

import android.content.Context;
import android.content.SharedPreferences;

import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.F;
import com.px.bmarket.Utils.ApkCheck;
import com.px.bmarket.Utils.FileDownload.DownloadManager;
import com.px.bmarket.Utils.FileDownload.OnDownloadListener;
import com.px.bmarket.Utils.Logger;
import com.px.bmarket.Utils.MD5;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by PX on 2016/9/14.
 */
public class VideoData implements IVideoData {

    private DownloadManager downloadManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String videoMD5;
    private Context context;
    private boolean isDownloading = false;

    public VideoData(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(F.sp.video , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public void loadData(OnCompletedListener onCompletedListener) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);

        new Retrofit.Builder().baseUrl(F.url.base_url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IVideoService.class)
                .getData()
                .enqueue(new Callback<VideoInfo>() {
                    @Override
                    public void onResponse(Call<VideoInfo> call, Response<VideoInfo> response) {
                        loadVideo(response.body());
                    }

                    @Override
                    public void onFailure(Call<VideoInfo> call, Throwable t) {
                        Logger.d(t.getMessage());
                    }
                });

    }

    public void loadVideo(VideoInfo videoInfo) {
        Logger.d(videoInfo.toString());
        videoMD5 = videoInfo.getMd5();
        int localVersion = sharedPreferences.getInt("version" ,0);
        isDownloading = sharedPreferences.getBoolean("isDownloading" ,false);
        if(!ApkCheck.isFileExists(F.path.video ,"btvi3.mp4") && !isDownloading){
            editor.putBoolean("isVideoCanPlay" ,false);
            editor.commit();
            downloadVideo(videoInfo);
        }else if(videoInfo.getVersion() > localVersion && !isDownloading){
            editor.putBoolean("isVideoCanPlay" ,false);
            editor.commit();
            downloadVideo(videoInfo);
        }else if(!isFileIntact(videoInfo.getMd5()) && !isDownloading){
            editor.putBoolean("isVideoCanPlay" ,false);
            editor.commit();
            downloadVideo(videoInfo);
        }else{
            editor.putBoolean("isVideoCanPlay" ,true);
            editor.commit();
            Logger.d("video can play");
        }
    }

    public void downloadVideo(final VideoInfo videoInfo){
        if(downloadManager==null){
            downloadManager = DownloadManager.getInstance(context);
        }
        downloadManager.startDownload("btvi3.mp4" ,videoInfo.getVideoUrl(),F.path.video);
        downloadManager.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onStart(int progress, boolean isStart) {
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
