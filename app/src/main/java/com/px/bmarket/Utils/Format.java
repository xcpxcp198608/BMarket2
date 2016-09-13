package com.px.bmarket.Utils;

/**
 * Created by PX on 2016/9/11.
 */
public class Format {
    public static int getProgress (long currentLength , long totalLength){
        return (int)(currentLength*100L/totalLength);
    }
}
