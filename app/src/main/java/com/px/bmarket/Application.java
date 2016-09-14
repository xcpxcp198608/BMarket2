package com.px.bmarket;

import android.content.SharedPreferences;

import com.px.bmarket.Beans.VideoInfo;
import com.px.bmarket.Data.VideoData.IVideoService;
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

/**
 * Created by PX on 2016/9/11.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
