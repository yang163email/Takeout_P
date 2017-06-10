package com.yan.takeout.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.dao.AddressDao;
import com.yan.takeout.model.dao.ReceiptAddress;
import com.yan.takeout.util.RecycleViewDivider;
import com.yan.takeout.view.adapter.AddressRvAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class ReceiptAddressActivity extends Activity {
    @Bind(R.id.ib_back)
    ImageButton mIbBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @Bind(R.id.tv_add_address)
    TextView mTvAddAddress;
    private AddressDao mAddressDao;
    private AddressRvAdapter mAddressRvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.bind(this);
        mAddressDao = new AddressDao(this);

        mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvReceiptAddress.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mAddressRvAdapter = new AddressRvAdapter(this);
        mRvReceiptAddress.setAdapter(mAddressRvAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<ReceiptAddress> receiptAddresses = mAddressDao.queryAllAddress();
        if(receiptAddresses != null) {
            mAddressRvAdapter.setAddressList(receiptAddresses);
        }
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                //新增地址
                Intent intent = new Intent(this, AddReceiptAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
