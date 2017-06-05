package com.yan.takeout.presenter;

import com.yan.takeout.util.Constant;
import com.yan.takeout.view.fragment.HomeFragment;

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
        retrofit.create(TakeoutService.class);

    }
}
