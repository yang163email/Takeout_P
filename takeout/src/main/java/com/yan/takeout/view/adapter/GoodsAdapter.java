package com.yan.takeout.view.adapter;

import android.content.Context;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GoodsInfo goodsInfo) {
            mTvName.setText(goodsInfo.getName());

            Log.d(TAG, "setData: " + goodsInfo.getIcon());
            Picasso.with(mContext).load(goodsInfo.getIcon()).into(mIvIcon);
            mTvZucheng.setText(goodsInfo.getForm());
            mTvYueshaoshounum.setText("月售" + goodsInfo.getMonthSaleNum() + "份");
            mTvNewprice.setText(PriceFormater.format(Float.parseFloat(goodsInfo.getNewPrice())));
            mTvOldprice.setText(PriceFormater.format(goodsInfo.getOldPrice()));
        }
    }
}