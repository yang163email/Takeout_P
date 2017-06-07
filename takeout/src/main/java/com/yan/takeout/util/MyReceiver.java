package com.yan.takeout.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 楠GG on 2017/6/7.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
//        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//        Log.d(TAG, "onReceive: title:" + title);

        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d(TAG, "onReceive: message:" + message);

        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "onReceive: extras:" + extras);
    }
}
