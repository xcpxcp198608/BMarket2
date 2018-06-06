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
        public static final String base_url = "http://market.v2.ldlegacy.com:8085/market/";
        public static final String video_info = base_url+"video/";
        public static final String app_info = base_url+"app/";
        public static final String button_info = base_url+"button/";
        public static final String app_market = base_url+"upgrade/";
        public static final String roll_view_info = base_url+"rollimage/";
        public static final String marquee_info = base_url+"marquee/";
    }

    public static final class sp{
        public static final String video = "Video";
    }
}
