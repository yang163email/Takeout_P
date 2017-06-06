package com.yan.takeout.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.model.net.User;
import com.yan.takeout.view.activity.LoginActivity;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class LoginActivityPresenter extends NetPresenter {
    private static final String TAG = "LoginActivityPresenter";
    private LoginActivity mLoginActivity;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

    public void loginByPhone(String phone, int type) {
        //type：1，表示普通账号；2，表示手机号码账号
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
    protected void onSuccess(String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        Log.d(TAG, "onSuccess: userId:" + user.getId());
    }
}
