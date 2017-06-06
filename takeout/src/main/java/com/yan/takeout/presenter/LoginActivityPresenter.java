package com.yan.takeout.presenter;

import com.yan.takeout.model.net.ResponseInfo;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class LoginActivityPresenter extends NetPresenter {

    public void loginByPhone(String phone, int type) {
        Call<ResponseInfo> call = mTakeoutService.loginByPhone(phone, type);
        call.enqueue(mCallback);
    }

    @Override
    protected void onConnectError(String message) {

    }

    @Override
    protected void onServerBug(int code) {

    }

    @Override
    protected void onSuccess(String data) {

    }
}
