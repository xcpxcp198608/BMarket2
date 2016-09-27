package com.px.bmarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.px.bmarket.Animator.Animator;
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
    public View getView(final ViewGroup container, final int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setBackgroundResource(R.drawable.bg);
        imageView.setPadding(2,2,2,2);
        imageView.setFocusable(true);
        imageView.setClickable(true);
        Picasso.with(container.getContext())
                .load(list.get(position).getImageUrl())
                .placeholder(R.drawable.btvi3_normal)
                .error(R.drawable.btvi3_normal)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Animator.zoomIn09_10(v);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.getContext().startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(list.get(position).getLinkUrl())));
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
