package com.px.bmarket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PX on 2016/9/12.
 */
public class AppsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<AppInfo> list;

    public AppsAdapter(Context context, List<AppInfo> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_app , null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_App);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_App);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AppInfo appInfo = list.get(position);
        viewHolder.textView.setText(appInfo.getApkName());
        Picasso.with(context).load(appInfo.getApkIconUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imageView);
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
