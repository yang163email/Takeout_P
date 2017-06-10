package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class CartRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList;

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    public CartRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mGoodsInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_type_all_price)
        TextView mTvTypeAllPrice;
        @Bind(R.id.ib_minus)
        ImageButton mIbMinus;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.ib_add)
        ImageButton mIbAdd;
        @Bind(R.id.ll)
        LinearLayout mLl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_minus, R.id.ib_add})
        public void onAddOrMinusClick(View view) {
            switch (view.getId()) {
                case R.id.ib_minus:
                    //减去货物
                    break;
                case R.id.ib_add:
                    //添加
                    break;
            }
        }

        public void setData(GoodsInfo goodsInfo) {
            mTvName.setText(goodsInfo.getName());

        }
    }
}
