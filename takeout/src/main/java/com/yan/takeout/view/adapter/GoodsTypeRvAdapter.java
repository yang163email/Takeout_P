package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsTypeInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/8.
 */

public class GoodsTypeRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsTypeInfo> mGoodsTypeInfoList = new ArrayList<>();

    public void setGoodsTypeInfoList(List<GoodsTypeInfo> goodsTypeInfoList) {
        mGoodsTypeInfoList = goodsTypeInfoList;
        notifyDataSetChanged();
    }

    public GoodsTypeRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mGoodsTypeInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGoodsTypeInfoList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tvCount)
        TextView mTvCount;
        @Bind(R.id.type)
        TextView mType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(GoodsTypeInfo goodsTypeInfo) {
            mType.setText(goodsTypeInfo.getName());
        }
    }
}
