package com.yan.takeout.presenter;

import com.yan.takeout.model.net.ResponseInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 将所有接口都放入这个接口中
 */

public interface TakeoutService {

    @GET("home")
    Call<ResponseInfo> getHomeInfo();

    @GET("login")
    Call<ResponseInfo> loginByPhone(@Query("phone") String phone, @Query("type")int type);

    @GET("order")
    Call<ResponseInfo> getOrderList(@Query("userId") String userId);

    @GET("business")
    Call<ResponseInfo> getBusinessInfo(@Query("sellerId") int sellerId);
}
