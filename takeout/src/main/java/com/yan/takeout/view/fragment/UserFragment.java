package com.yan.takeout.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.view.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class UserFragment extends Fragment {

    @Bind(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @Bind(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @Bind(R.id.login)
    ImageView mLogin;
    @Bind(R.id.username)
    TextView mUsername;
    @Bind(R.id.phone)
    TextView mPhone;
    @Bind(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @Bind(R.id.iv_address_manager)
    ImageView mIvAddressManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user, null);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
}
