package com.px.bmarket;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by PX on 2016/9/11.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }
}
