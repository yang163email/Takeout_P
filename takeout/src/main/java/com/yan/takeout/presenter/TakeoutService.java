package com.yan.takeout.presenter;

import com.yan.takeout.model.net.ResponseInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 将所有接口都放入这个接口中
 */

public interface TakeoutService {

    @GET("home")
    Call<ResponseInfo> getHomeInfo();
}
