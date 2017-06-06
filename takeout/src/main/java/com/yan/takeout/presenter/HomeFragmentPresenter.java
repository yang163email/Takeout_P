package com.yan.takeout.presenter;

import com.yan.takeout.bean.net.ResponseInfo;
import com.yan.takeout.view.fragment.HomeFragment;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class HomeFragmentPresenter extends NetPresenter {
    private HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo() {
        Call<ResponseInfo> call = mTakeoutService.getHomeInfo();
        call.enqueue(mCallback);
    }

    @Override
    protected void onConnectError(String message) {
        mHomeFragment.onHomeError(message);
    }

    @Override
    protected void onServerBug(int code) {
        mHomeFragment.onHomeFailed(code);
    }

    @Override
    protected void onSuccess(String data) {
        mHomeFragment.onHomeSuccess(data);
    }
}
