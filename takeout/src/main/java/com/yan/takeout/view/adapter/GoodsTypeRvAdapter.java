package com.yan.takeout.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.view.fragment.GoodsFragment;

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
    private GoodsFragment mGoodsFragment;

    public void setGoodsFragment(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
    }

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
        viewHolder.setData(mGoodsTypeInfoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mGoodsTypeInfoList.size();
    }

    /**当前选中的位置*/
    public int mSelectedPosition = 0;

    class ViewHolder extends RecyclerView.ViewHolder{
        private final View mView;
        @Bind(R.id.tvCount)
        TextView mTvCount;
        @Bind(R.id.type)
        TextView mType;
        private int mPosition;
        private GoodsTypeInfo mGoodsTypeInfo;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedPosition = mPosition;
                    notifyDataSetChanged();

                    //计算点击左侧时，右侧显示对应商品位置
                    int typeId = mGoodsTypeInfo.getId();
                    int goodsPos = mGoodsFragment.mGoodsFragmentPresenter.getGoodsPosByTypeId(typeId);

                    //让右侧选中
                    mGoodsFragment.mSlhlv.setSelection(goodsPos);
                }
            });
        }

        public void setData(GoodsTypeInfo goodsTypeInfo, int position) {
            mGoodsTypeInfo = goodsTypeInfo;
            mPosition = position;

            if(mSelectedPosition == mPosition) {
                //选中效果
                mView.setBackgroundColor(Color.WHITE);
                mType.setTextColor(Color.BLACK);
                mType.setTypeface(Typeface.DEFAULT_BOLD);
            }else {
                mView.setBackgroundColor(Color.parseColor("#b9dedcdc"));
                mType.setTextColor(Color.GRAY);
                mType.setTypeface(Typeface.DEFAULT);
            }
            mType.setText(goodsTypeInfo.getName());

            if(goodsTypeInfo.getRedCount() == 0) {
                //不显示红点
                mTvCount.setVisibility(View.INVISIBLE);
            }else {
                //显示
                mTvCount.setVisibility(View.VISIBLE);
                mTvCount.setText(goodsTypeInfo.getRedCount() + "");
            }
        }
    }
}
