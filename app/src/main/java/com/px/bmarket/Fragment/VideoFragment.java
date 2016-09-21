package com.px.bmarket.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.px.bmarket.Activity.DownloadActivity;
import com.px.bmarket.Adapter.AppsAdapter;
import com.px.bmarket.Animator.Animator;
import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.R;
import com.px.bmarket.SQLite.SQLiteDao;

import java.util.List;

/**
 * Created by PX on 2016/9/21.
 */
public class VideoFragment extends Fragment {
    private GridView gv_Video;
    private List<AppInfo> list;
    private AppsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        gv_Video = (GridView) view.findViewById(R.id.gv_video);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list = SQLiteDao.getInstance(getContext()).queryByType("movie");
        adapter = new AppsAdapter(getContext() ,list);
        gv_Video.setAdapter(adapter);
        gv_Video.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gv_Video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = list.get(position);
                Intent intent = new Intent(getContext(), DownloadActivity.class);
                intent.putExtra("appInfo",appInfo);
                startActivity(intent);
            }
        });
    }
}
