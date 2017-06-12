package com.yan.takeout.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.util.OrderObservable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL;
import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE;
import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL;

/**
 * Created by 楠GG on 2017/6/12.
 */

public class OrderDetailActivity extends Activity {
    @Bind(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @Bind(R.id.tv_seller_name)
    TextView mTvSellerName;
    @Bind(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;
    @Bind(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @Bind(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        processIntent();
    }

    private void processIntent() {
        if(getIntent() != null) {
            String orderId = getIntent().getStringExtra("orderId");
            String type = getIntent().getStringExtra("type");
            int index = getIndex(type);

            ((TextView) mLlOrderDetailTypeContainer.getChildAt(index))
                    .setTextColor(Color.BLUE);
            ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(index))
                    .setImageResource(R.drawable.order_time_node_disabled);
        }
    }

    private int getIndex(String type) {
        int index = -1;
//        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
            case ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
//                typeInfo = "配送中";
                index = 2;
                break;
            case OrderObservable.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }
        return index;
    }

    @OnClick({R.id.iv_order_detail_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_order_detail_back:
                finish();
                break;
        }
    }
}
