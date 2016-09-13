package com.px.bmarket.Presenter;

import java.lang.ref.WeakReference;

/**
 * Created by PX on 2016/9/11.
 */
public abstract class BasePresenter <T>{
    protected WeakReference<T> mViewRef;

    public void attachView (T view){
        mViewRef = new WeakReference<T>(view);
    }

    public void detachView (){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef =null;
        }
    }

    protected T getView (){
        return mViewRef.get();
    }

}
