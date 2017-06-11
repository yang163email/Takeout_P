package com.yan.takeout.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.Seller;
import com.yan.takeout.util.PriceFormater;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/11.
 */

public class ConfirmOrderActivity extends Activity {
    @Bind(R.id.ib_back)
    ImageButton mIbBack;
    @Bind(R.id.iv_location)
    ImageView mIvLocation;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_label)
    TextView mTvLabel;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.iv_arrow)
    ImageView mIvArrow;
    @Bind(R.id.rl_location)
    RelativeLayout mRlLocation;
    @Bind(R.id.iv_icon)
    ImageView mIvIcon;
    @Bind(R.id.tv_seller_name)
    TextView mTvSellerName;
    @Bind(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @Bind(R.id.tv_deliveryFee)
    TextView mTvDeliveryFee;
    @Bind(R.id.tv_CountPrice)
    TextView mTvCountPrice;
    @Bind(R.id.tvSubmit)
    TextView mTvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);

        processIntent();
    }

    private void processIntent() {
        if(getIntent() != null) {
            Seller seller = (Seller) getIntent().getSerializableExtra("seller");
            List<GoodsInfo> cartList = (List<GoodsInfo>) getIntent().getSerializableExtra("cartList");

            mTvSellerName.setText(seller.getName());
            mTvDeliveryFee.setText(PriceFormater.format(Float.parseFloat(seller.getDeliveryFee())));

            float countPrice = Float.parseFloat(seller.getDeliveryFee());
            for (GoodsInfo goodsInfo : cartList) {
                countPrice += Float.parseFloat(goodsInfo.getNewPrice()) * goodsInfo.getCount();
            }
            mTvCountPrice.setText("待支付" + PriceFormater.format(countPrice));
        }
    }

    @OnClick({R.id.iv_arrow, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow:
                break;
            case R.id.tvSubmit:
                break;
        }
    }
}
