package com.yan.takeout.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.model.dao.ReceiptAddress;
import com.yan.takeout.view.activity.AddOrUpdateAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class AddressRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ReceiptAddress> mAddresses = new ArrayList<>();

    public AddressRvAdapter(Context context) {
        mContext = context;
    }

    public void setAddressList(List<ReceiptAddress> addresses) {
        mAddresses = addresses;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_receipt_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        if(mAddresses != null) {
            return mAddresses.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_sex)
        TextView mTvSex;
        @Bind(R.id.tv_phone)
        TextView mTvPhone;
        @Bind(R.id.tv_label)
        TextView mTvLabel;
        @Bind(R.id.tv_address)
        TextView mTvAddress;
        @Bind(R.id.iv_edit)
        ImageView mIvEdit;
        private ReceiptAddress mAddress;

        @OnClick(R.id.iv_edit)
        public void onclick(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddOrUpdateAddressActivity.class);
                    intent.putExtra("address", mAddress);
                    mContext.startActivity(intent);
                }
            });
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(ReceiptAddress address) {
            mAddress = address;
            mTvName.setText(address.getName());
            mTvSex.setText(address.getSex());
            mTvPhone.setText(address.getPhone() + "," + address.getPhoneOther());
            mTvAddress.setText(address.getAddress() + "," + address.getDetailAddress());
            mTvLabel.setText(address.getSelectLabel());
            mTvLabel.setTextColor(Color.BLACK);
        }
    }
}
