package com.px.bmarket;

import android.os.Environment;

/**
 * Created by PX on 2016/9/11.
 */
public final class F {

    public static final class tag{
        public static final String tag = "----px----";
    }

    public static final class path{
        public static final String video = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/Video/";
        public static final String market = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/Market/";
        public static final String apps = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/Apps/";
    }

    public static final class url{
        public static final String base_url = "http://158.69.229.104:8092/";
        public static final String app_market = "/json/AppMarketInfo.json";
        public static final String app_info = "/json/AppInfo.json";
        public static final String roll_view_info = "/json/RollImageInfo.json";
        public static final String marquee_info = "/json/MarqueeInfo.json";
        public static final String video_info = "/json/VideoInfo.json";
        public static final String button_info = "/json/ButtonInfo.json";
    }

    public static final class sp{
        public static final String video = "Video";
    }
}
