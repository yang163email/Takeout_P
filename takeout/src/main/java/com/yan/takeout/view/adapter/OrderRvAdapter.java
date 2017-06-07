package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/7.
 */

public class OrderRvAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public OrderRvAdapter(Context context) {
        mContext = context;
    }

    private List<Order> mOrderList = new ArrayList<>();

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item, null);
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
            mTvOrderItemType.setText(order.type + "");
        }
    }
}
