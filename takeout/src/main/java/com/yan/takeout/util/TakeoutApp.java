package com.yan.takeout.util;

import android.app.Application;

import com.yan.takeout.model.net.User;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class TakeoutApp extends Application {

    public static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();

        sUser = new User();
        //初始状态为-1
        sUser.setId(-1);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
