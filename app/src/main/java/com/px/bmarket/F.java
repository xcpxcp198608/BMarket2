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
        public static final String video = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/";
        public static final String market = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/";
        public static final String apps = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BMarket/";
    }

    public static final class url{
        public static final String video_info = "http://appota.gobeyondtv.co:8082/json/VideoInfo.json";
        public static final String app_info = "http://appota.gobeyondtv.co:8082/json/AppInfo.json";
        public static final String base_url = "http://appota.gobeyondtv.co:8082/";
        public static final String app_market = "http://appota.gobeyondtv.co:8080/MarketAd/GetUpdate";
        public static final String roll_view_info = "http://appota.gobeyondtv.co:8080/MarketAd/GetImage";
        public static final String marquee_info = "http://appota.gobeyondtv.co:8080/MarketAd/GetMarquee";
        public static final String button_info = "http://appota.gobeyondtv.co:8082/json/ButtonInfo.json";
    }

    public static final class sp{
        public static final String video = "Video";
    }
}
