package com.yan.takeout.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.yan.takeout.model.dao.TakeoutOpenHelper;
import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.model.net.User;
import com.yan.takeout.util.TakeoutApp;
import com.yan.takeout.view.activity.LoginActivity;

import java.sql.SQLException;
import java.sql.Savepoint;

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

        //保存在内存中
        TakeoutApp.sUser = user;

        //保存到Ormlite数据库中
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(mLoginActivity);

        //开启事务
        AndroidDatabaseConnection databaseConnection = new AndroidDatabaseConnection(
                takeoutOpenHelper.getWritableDatabase(), true);
        Savepoint startPoint = null;
        try {
            //设置保存点
            startPoint = databaseConnection.setSavePoint("start");
            //取消自动提交
            databaseConnection.setAutoCommit(false);

            Dao<User, Integer> dao = takeoutOpenHelper.getDao(User.class);
            //查询记录
            User oldUser = dao.queryForId(36);
            if (oldUser != null) {
                //老用户
                dao.update(user);
                Log.d(TAG, "onSuccess: 已经有此用户，更新信息");
            } else {
                //新用户
                dao.create(user);
                Log.d(TAG, "onSuccess: 创建新用户");
            }
//            dao.createIfNotExists(user);

            //提交事务
            databaseConnection.commit(startPoint);
            //登录成功
            mLoginActivity.onLoginSuccess();
            Log.d(TAG, "onSuccess: 提交事务");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "onSuccess: ormlite异常");
            try {
                //回滚记录点
                databaseConnection.rollback(startPoint);
                Log.d(TAG, "onSuccess: 异常，回滚事务");
            } catch (SQLException e1) {
                e1.printStackTrace();
                mLoginActivity.onLoginFailed();
            }
        }
    }
}
