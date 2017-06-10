package com.yan.takeout.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.Toast;

import com.yan.takeout.R;
import com.yan.takeout.model.dao.AddressDao;
import com.yan.takeout.model.dao.ReceiptAddress;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class AddOrUpdateAddressActivity extends Activity {
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
    private AddressDao mAddressDao;
    private ReceiptAddress mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.bind(this);
        processIntent();
        mAddressDao = new AddressDao(this);

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

    private void processIntent() {
        if(getIntent() != null) {
            mAddress = (ReceiptAddress) getIntent().getSerializableExtra("address");
            if(mAddress != null) {
                //更新
                mTvTitle.setText("修改地址");
                mEtName.setText(mAddress.getName());
                String sex = mAddress.getSex();
                if("先生".equals(sex)) {
                    mRbMan.setChecked(true);
                }else {
                    mRbWomen.setChecked(true);
                }
                mEtPhone.setText(mAddress.getPhone());
                mEtPhoneOther.setText(mAddress.getPhoneOther());
                mEtReceiptAddress.setText(mAddress.getAddress());
                mEtDetailAddress.setText(mAddress.getDetailAddress());
                mTvLabel.setText(mAddress.getSelectLabel());
            }else {
                //新增
                mTvTitle.setText("新增地址");
            }
        }
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
                alertSelectLabelDialog();
                break;
            case R.id.bt_ok:
                boolean isOk = checkReceiptAddressInfo();
                if(isOk) {
                    if(mAddress != null) {
                        //修改
                        String name = mEtName.getText().toString().trim();
                        String sex = "女士";
                        if (mRbMan.isChecked()) {
                            sex = "先生";
                        }
                        String phone = mEtPhone.getText().toString().trim();
                        String phoneOther = mEtPhoneOther.getText().toString().trim();
                        String address = mEtReceiptAddress.getText().toString().trim();
                        String detailAddress = mEtDetailAddress.getText().toString().trim();
                        String selectLabel = mTvLabel.getText().toString();

                        mAddress.setName(name);
                        mAddress.setSex(sex);
                        mAddress.setPhone(phone);
                        mAddress.setPhoneOther(phoneOther);
                        mAddress.setAddress(address);
                        mAddress.setDetailAddress(detailAddress);
                        mAddress.setSelectLabel(selectLabel);


                        boolean ok = mAddressDao.updateAddress(mAddress);
                        if (ok) {
                            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "请仔细检查您的数据", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //把地址保存在本地数据库
//                    int id, String name, String sex, String phone, String phoneOther, String address, String detailAddress, String selectLabel, String userId
                        String name = mEtName.getText().toString().trim();
                        String sex = "女士";
                        if (mRbMan.isChecked()) {
                            sex = "先生";
                        }
                        String phone = mEtPhone.getText().toString().trim();
                        String phoneOther = mEtPhoneOther.getText().toString().trim();
                        String address = mEtReceiptAddress.getText().toString().trim();
                        String detailAddress = mEtDetailAddress.getText().toString().trim();
                        String selectLabel = mTvLabel.getText().toString();

                        boolean ok = mAddressDao.insertAddress(new ReceiptAddress(8888, name, sex, phone,
                                phoneOther, address, detailAddress, selectLabel, "36"));
                        if (ok) {
                            finish();
                        } else {
                            Toast.makeText(this, "请仔细检查您的数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private String[] mTitles = {"无", "学校", "公司", "家"};
    private String[] mBgColors = new String[]{"#FFE6AA6A", "#FF32E9DA", "#FF9432E9", "#FF51E932"};

    /**弹出选择标签dialog*/
    private void alertSelectLabelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择地址标签");
        builder.setItems(mTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvLabel.setText(mTitles[which]);
                mTvLabel.setTextColor(Color.BLACK);
                mTvLabel.setBackgroundColor(Color.parseColor(mBgColors[which]));
            }
        });
        builder.show();
    }

    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }
}
