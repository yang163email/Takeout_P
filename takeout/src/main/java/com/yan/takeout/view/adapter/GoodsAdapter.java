package com.yan.takeout.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.util.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by 楠GG on 2017/6/8.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private static final String TAG = "GoodsAdapter";

    private Context mContext;
    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);

        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_type_header, parent, false);

        ((TextView) headerView).setText(goodsInfo.getTypeName());
        return headerView;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);
        return goodsInfo.getTypeId();
    }

    @Override
    public int getCount() {
        return mGoodsInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(mGoodsInfoList.get(position));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView mIvIcon;
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_zucheng)
        TextView mTvZucheng;
        @Bind(R.id.tv_yueshaoshounum)
        TextView mTvYueshaoshounum;
        @Bind(R.id.tv_newprice)
        TextView mTvNewprice;
        @Bind(R.id.tv_oldprice)
        TextView mTvOldprice;
        @Bind(R.id.ib_minus)
        ImageButton mIbMinus;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.ib_add)
        ImageButton mIbAdd;
        private GoodsInfo mGoodsInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_add, R.id.ib_minus})
        public void onAddOrMinusClick(View v) {
            switch (v.getId()) {
                case R.id.ib_add:
                    //处理增加商品数量
                    doAddOperation();
                    break;
                case R.id.ib_minus:
                    //处理减去商品数
                    doMinusOperation();
            }
        }

        private void doMinusOperation() {

        }

        /**增加商品数*/
        private void doAddOperation() {
            int count = mGoodsInfo.getCount();
            count ++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();
        }

        public void setData(GoodsInfo goodsInfo) {
            mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());

            Log.d(TAG, "setData: " + goodsInfo.getIcon());
            Picasso.with(mContext).load(goodsInfo.getIcon()).into(mIvIcon);
            mTvZucheng.setText(goodsInfo.getForm());
            mTvYueshaoshounum.setText("月售" + goodsInfo.getMonthSaleNum() + "份");
            mTvNewprice.setText(PriceFormater.format(Float.parseFloat(goodsInfo.getNewPrice())));

            if(goodsInfo.getOldPrice() == 0) {
                mTvOldprice.setVisibility(View.GONE);
            }else {
                mTvOldprice.setVisibility(View.VISIBLE);
                mTvOldprice.setText(PriceFormater.format(goodsInfo.getOldPrice()));
                //废除线
                mTvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            int count = goodsInfo.getCount();
            if(count > 0) {
                //显示
                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);
            }else {
                //不显示
                mTvCount.setVisibility(View.GONE);
                mIbMinus.setVisibility(View.GONE);
            }
            mTvCount.setText(count + "");
        }
    }
}
