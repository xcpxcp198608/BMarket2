package com.px.bmarket.Activity;

import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.Beans.MarqueeInfo;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.Beans.VideoInfo;

import java.util.List;

/**
 * Created by PX on 2016/9/12.
 */
public interface IMainActivity {
    void loadRollImage(List<RollImageInfo> rollImageInfos);
    void loadMarquee (MarqueeInfo marqueeInfo);
    void loadButton(List<ButtonInfo> buttonInfos);
    void loadVideo(VideoInfo videoInfo);
}
