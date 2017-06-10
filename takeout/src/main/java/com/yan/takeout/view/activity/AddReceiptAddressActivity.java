package com.yan.takeout.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yan.takeout.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class AddReceiptAddressActivity extends Activity {
    @Bind(R.id.ib_back)
    ImageButton mIbBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.ib_delete)
    ImageButton mIbDelete;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.rb_man)
    RadioButton mRbMan;
    @Bind(R.id.rb_women)
    RadioButton mRbWomen;
    @Bind(R.id.rg_sex)
    RadioGroup mRgSex;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @Bind(R.id.ib_add_phone_other)
    ImageButton mIbAddPhoneOther;
    @Bind(R.id.et_phone_other)
    EditText mEtPhoneOther;
    @Bind(R.id.ib_delete_phone_other)
    ImageButton mIbDeletePhoneOther;
    @Bind(R.id.rl_phone_other)
    RelativeLayout mRlPhoneOther;
    @Bind(R.id.et_receipt_address)
    EditText mEtReceiptAddress;
    @Bind(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @Bind(R.id.tv_label)
    TextView mTvLabel;
    @Bind(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @Bind(R.id.bt_ok)
    Button mBtOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.bind(this);

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)) {
                    mIbDeletePhone.setVisibility(View.VISIBLE);
                }else {
                    mIbDeletePhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPhoneOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)) {
                    mIbDeletePhoneOther.setVisibility(View.VISIBLE);
                }else {
                    mIbDeletePhoneOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.ib_back, R.id.ib_delete, R.id.ib_delete_phone, R.id.ib_add_phone_other, R.id.ib_delete_phone_other, R.id.ib_select_label, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete:
                break;
            case R.id.ib_delete_phone:
                //删除号码
                mEtPhone.setText("");
                break;
            case R.id.ib_add_phone_other:
                //新增号码
                mRlPhoneOther.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_delete_phone_other:
                mEtPhoneOther.setText("");
                break;
            case R.id.ib_select_label:
                break;
            case R.id.bt_ok:
                break;
        }
    }
}
