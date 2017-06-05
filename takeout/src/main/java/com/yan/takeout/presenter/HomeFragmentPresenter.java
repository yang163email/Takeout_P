package com.yan.takeout.presenter;

import com.yan.takeout.bean.net.ResponseInfo;
import com.yan.takeout.util.Constant;
import com.yan.takeout.view.fragment.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class HomeFragmentPresenter {
    private HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.HOST)
                .addConverterFactory(GsonConverterFactory.create()).build();
        TakeoutService takeoutService = retrofit.create(TakeoutService.class);
        Call<ResponseInfo> call = takeoutService.getHomeInfo();
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                if(response.isSuccessful()) {
                    //响应成功
                    mHomeFragment.onHomeSuccess();
                }else {
                    //响应失败，服务器代码问题
                    mHomeFragment.onHomeFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                //连接失败，网络问题
                mHomeFragment.onHomeError();
            }
        });
    }
}
