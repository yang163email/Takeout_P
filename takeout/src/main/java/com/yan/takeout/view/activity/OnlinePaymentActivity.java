package com.yan.takeout.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.Seller;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/11.
 */

public class OnlinePaymentActivity extends Activity {
    @Bind(R.id.ib_back)
    ImageButton mIbBack;
    @Bind(R.id.tv_residualTime)
    TextView mTvResidualTime;
    @Bind(R.id.tv_order_name)
    TextView mTvOrderName;
    @Bind(R.id.tv)
    TextView mTv;
    @Bind(R.id.tv_order_detail)
    TextView mTvOrderDetail;
    @Bind(R.id.iv_triangle)
    ImageView mIvTriangle;
    @Bind(R.id.ll_order_toggle)
    RelativeLayout mLlOrderToggle;
    @Bind(R.id.tv_receipt_connect_info)
    TextView mTvReceiptConnectInfo;
    @Bind(R.id.tv_receipt_address_info)
    TextView mTvReceiptAddressInfo;
    @Bind(R.id.ll_goods)
    LinearLayout mLlGoods;
    @Bind(R.id.ll_order_detail)
    LinearLayout mLlOrderDetail;
    @Bind(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @Bind(R.id.iv_pay_alipay)
    ImageView mIvPayAlipay;
    @Bind(R.id.cb_pay_alipay)
    CheckBox mCbPayAlipay;
    @Bind(R.id.tv_selector_other_payment)
    TextView mTvSelectorOtherPayment;
    @Bind(R.id.ll_hint_info)
    LinearLayout mLlHintInfo;
    @Bind(R.id.iv_pay_wechat)
    ImageView mIvPayWechat;
    @Bind(R.id.cb_pay_wechat)
    CheckBox mCbPayWechat;
    @Bind(R.id.iv_pay_qq)
    ImageView mIvPayQq;
    @Bind(R.id.cb_pay_qq)
    CheckBox mCbPayQq;
    @Bind(R.id.iv_pay_fenqile)
    ImageView mIvPayFenqile;
    @Bind(R.id.cb_pay_fenqile)
    CheckBox mCbPayFenqile;
    @Bind(R.id.ll_other_payment)
    LinearLayout mLlOtherPayment;
    @Bind(R.id.bt_confirm_pay)
    Button mBtConfirmPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.bind(this);

        processIntent();
    }

    private void processIntent() {
        if(getIntent() != null) {
            Seller seller = (Seller) getIntent().getSerializableExtra("seller");
            List<GoodsInfo> cartList = (List<GoodsInfo>) getIntent().getSerializableExtra("cartList");
            float countPrice = getIntent().getFloatExtra("countPrice", 0.01f);

            mTvPayMoney.setText("￥0.01");
        }
    }

    @OnClick({R.id.iv_triangle, R.id.bt_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_triangle:
                break;
            case R.id.bt_confirm_pay:
                //确认支付
                break;
        }
    }
}
