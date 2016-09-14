package com.px.bmarket.Data.RollImageData;

import com.px.bmarket.Beans.RollImageInfo;
import com.px.bmarket.F;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PX on 2016/9/12.
 */
public interface IRollImageService {
    @GET(F.url.roll_view_info)
    Call<List<RollImageInfo>> getData();
}
