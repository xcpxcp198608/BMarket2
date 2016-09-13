package com.px.bmarket.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.px.bmarket.F;
import com.px.bmarket.Presenter.BasePresenter;
import com.px.bmarket.Utils.Logger;

/**
 * Created by PX on 2016/9/11.
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {
    protected T presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.init(F.tag.tag);

        presenter = createPresenter();
        presenter.attachView((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract T createPresenter();
}
