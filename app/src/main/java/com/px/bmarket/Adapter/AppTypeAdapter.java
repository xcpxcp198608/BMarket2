package com.px.bmarket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.px.bmarket.R;

/**
 * Created by PX on 2016/9/12.
 */
public class AppTypeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] text ;
    private int [] resId ;

    public AppTypeAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        text = new String[] {context.getString(R.string.text_recommend) ,context.getString(R.string.text_video),context.getString(R.string.text_ktv)
                ,context.getString(R.string.text_game),context.getString(R.string.text_im)
                ,context.getString(R.string.text_music),context.getString(R.string.text_tools)};
        resId = new int[]{R.drawable.recommend,R.drawable.video,R.drawable.music,R.drawable.game,R.drawable.im,R.drawable.music,R.drawable.tools};
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_app_type , null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_AppType);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_AppType);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(text[position]);
        viewHolder.imageView.setImageResource(resId[position]);
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
