package com.yan.takeout.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.takeout.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.iv_user_back)
    ImageView mIvUserBack;
    @Bind(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @Bind(R.id.et_user_phone)
    EditText mEtUserPhone;
    @Bind(R.id.tv_user_code)
    TextView mTvUserCode;
    @Bind(R.id.et_user_code)
    EditText mEtUserCode;
    @Bind(R.id.login)
    TextView mLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SMSSDK.initSDK(this, "1e1a9e5d7c0fd", "cfa0964301f7aae44135ad2f75471594");

        SMSSDK.registerEventHandler(mEventHandler);
    }

    EventHandler mEventHandler = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @OnClick({R.id.tv_user_code, R.id.login})
    public void onViewClicked(View view) {
        String phone = mEtUserPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_user_code:
                //发送获取验证码
                SMSSDK.getVerificationCode("86", phone);
                break;
            case R.id.login:
                //登录验证
                String code = mEtUserCode.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", phone, code);
                break;
        }
    }
}
