package com.px.bmarket.Animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by PX on 2016/9/13.
 */
public class Animator {

    public static void zoomIn09_10(View view){
        ObjectAnimator zX = ObjectAnimator.ofFloat(view,"scaleX" ,0.9f,1.0f);
        ObjectAnimator zY = ObjectAnimator.ofFloat(view,"scaleY" ,0.9f,1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(zX).with(zY);
        animatorSet.setDuration(150);
        animatorSet.start();
    }

    public static void marquee(View view ,float x1){
        float x = view.getTranslationX();
        ObjectAnimator zX = ObjectAnimator.ofFloat(view,"translationX" ,x,x1);
        zX.setRepeatCount(0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(zX);
        animatorSet.setDuration(20000);
        animatorSet.start();
    }
}
