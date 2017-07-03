package com.px.bmarket.Utils.OkHttp.Listener;


import com.px.bmarket.Utils.OkHttp.Bean.DownloadInfo;

/**
 * Created by patrick on 2016/12/23.
 */

public interface DownloadListener  {
     void onPending(DownloadInfo downloadInfo);
     void onStart(DownloadInfo downloadInfo);
     void onPause(DownloadInfo downloadInfo);
     void onProgress(DownloadInfo downloadInfo);
     void onFinished(DownloadInfo downloadInfo);
     void onCancel(DownloadInfo downloadInfo);
     void onError(DownloadInfo downloadInfo);

}
