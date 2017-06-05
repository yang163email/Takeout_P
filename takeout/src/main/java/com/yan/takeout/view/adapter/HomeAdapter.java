package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.takeout.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mDatas;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_home_common, null);
        HomeItemHolder homeItemHolder = new HomeItemHolder(itemView);
        return homeItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeItemHolder homeItemHolder = (HomeItemHolder) holder;
        homeItemHolder.setData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public void setData(List<String> datas) {
        //接收数据并刷新
        mDatas = datas;
        notifyDataSetChanged();
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView mTv;

        public HomeItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {
            mTv.setText(data);
        }
    }

}
