package com.yan.takeout.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.yan.takeout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 楠GG on 2017/6/12.
 */

public class AroundRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PoiItem> mPoiItems = new ArrayList<>();

    public void setPoiItems(List<PoiItem> poiItems) {
        mPoiItems = poiItems;
        notifyDataSetChanged();
    }

    public AroundRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_around, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mPoiItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mPoiItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @butterknife.Bind(R.id.title)
        TextView mTitle;
        @butterknife.Bind(R.id.address)
        TextView mAddress;
        private PoiItem mPoiItem;

        ViewHolder(View view) {
            super(view);
            butterknife.ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", mPoiItem.getTitle());
                    intent.putExtra("snippet", mPoiItem.getSnippet());
                    ((Activity) mContext).setResult(200, intent);
                    ((Activity) mContext).finish();
                }
            });
        }

        public void setData(PoiItem poiItem) {
            mPoiItem = poiItem;
            mTitle.setText(poiItem.getTitle());
            mAddress.setText(poiItem.getSnippet());
        }
    }
}
