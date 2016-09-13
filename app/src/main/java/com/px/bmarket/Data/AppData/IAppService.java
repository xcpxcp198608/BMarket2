package com.px.bmarket.Data.AppData;

import com.px.bmarket.Beans.AppInfo;
import com.px.bmarket.F;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by PX on 2016/9/12.
 */
public interface IAppService {
    @GET(F.url.app_info)
    Call<List<AppInfo>> getData();
}
