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
import com.px.bmarket.Utils.Logger;

import java.util.List;

/**
 * Created by PX on 2016/9/21.
 */
public class RecommendFragment extends Fragment {
    private GridView gv_Recommend;
    private List<AppInfo> list;
    private AppsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,container,false);
        gv_Recommend = (GridView) view.findViewById(R.id.gv_recommend);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list = SQLiteDao.getInstance(getContext()).queryRecommend();
        Logger.d(list.toString());
        adapter = new AppsAdapter(getContext() ,list);
        gv_Recommend.setAdapter(adapter);
        gv_Recommend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Animator.zoomIn09_10(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gv_Recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
