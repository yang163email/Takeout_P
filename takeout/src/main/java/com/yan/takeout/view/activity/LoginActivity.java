package com.yan.takeout.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yan.takeout.R;

import cn.smssdk.SMSSDK;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SMSSDK.initSDK(this, "您的appkey", "您的appsecret");
    }
}
