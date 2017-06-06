package com.yan.takeout.presenter;

import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 楠GG on 2017/6/6.
 */

public abstract class NetPresenter {

    protected Retrofit mRetrofit;
    protected TakeoutService mTakeoutService;

    public NetPresenter() {
        mRetrofit = new Retrofit.Builder().baseUrl(Constant.HOST)
                .addConverterFactory(GsonConverterFactory.create()).build();
        mTakeoutService = mRetrofit.create(TakeoutService.class);
    }

    protected Callback<ResponseInfo> mCallback = new Callback<ResponseInfo>() {
        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            if(response.isSuccessful()) {
                //响应成功
                ResponseInfo responseInfo = response.body();
                onSuccess(responseInfo.getData());
            }else {
                //响应失败，服务器代码问题
                onServerBug(response.code());
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //连接失败，网络问题
            onConnectError(t.getMessage());
        }
    };

    protected abstract void onConnectError(String message);

    protected abstract void onServerBug(int code);

    protected abstract void onSuccess(String jsonData);
}
