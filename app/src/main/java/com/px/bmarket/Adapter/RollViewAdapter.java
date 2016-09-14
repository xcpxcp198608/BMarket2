package com.px.bmarket.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PX on 2016/9/13.
 */
public class RollViewAdapter extends StaticPagerAdapter {

    private List<RollImageInfo> list;

    public RollViewAdapter( List<RollImageInfo> list) {
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        Picasso.with(container.getContext())
                .load(list.get(position).getImageUrl())
                .placeholder(R.drawable.btvi3)
                .error(R.drawable.btvi3)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
