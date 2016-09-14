package com.px.bmarket.Data.ButtonData;

import com.px.bmarket.Beans.ButtonInfo;
import com.px.bmarket.F;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PX on 2016/9/12.
 */
public interface IButtonService {
    @GET(F.url.button_info)
    Call<List<ButtonInfo>> getData();
}
