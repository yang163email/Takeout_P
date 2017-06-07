package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.Order;
import com.yan.takeout.util.OrderObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/7.
 */

public class OrderRvAdapter extends RecyclerView.Adapter implements Observer{
    private static final String TAG = "OrderRvAdapter";
    private Context mContext;

    public OrderRvAdapter(Context context) {
        mContext = context;
        OrderObservable.getInstance().addObserver(this);
    }

    private List<Order> mOrderList = new ArrayList<>();

    @Override
    public void update(Observable o, Object arg) {
        Log.d(TAG, "update: 收到自定义消息了");

        Map<String, String> data = (Map<String, String>) arg;
        String orderId = data.get("orderId");
        String type = data.get("type");

        int index = -1;
        for (int i = 0; i < mOrderList.size(); i++) {
            Order order = mOrderList.get(i);
            if(order.id.equals(orderId)) {
                order.type = type;
                index = i;
            }
        }
        if(index != -1) {
            //数据发生了变化
            notifyItemChanged(index);
        }
    }

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item, parent, false);
        OrderItemHolder orderItemHolder = new OrderItemHolder(itemView);
        return orderItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderItemHolder orderItemHolder = (OrderItemHolder) holder;
        orderItemHolder.setData(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mOrderList != null) {
            return mOrderList.size();
        }
        return 0;
    }

    private String getOrderTypeInfo(String type) {
        /**
         * 订单状态
         * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
         */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
                typeInfo = "已提交订单";
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
                typeInfo = "商家接单";
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
                typeInfo = "配送中";
                break;
            case OrderObservable.ORDERTYPE_SERVED:
                typeInfo = "已送达";
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
                typeInfo = "取消的订单";
                break;
        }
        return typeInfo;
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @Bind(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @Bind(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @Bind(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @Bind(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @Bind(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @Bind(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;

        OrderItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(Order order) {
            mTvOrderItemSellerName.setText(order.seller.getName());
            mTvOrderItemType.setText(getOrderTypeInfo(order.type));
        }
    }
}
