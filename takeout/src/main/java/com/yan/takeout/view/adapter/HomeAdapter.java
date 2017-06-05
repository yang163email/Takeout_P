package com.yan.takeout.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.yan.takeout.R;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeAdapter";

    private Context mContext;
    private List<String> mDatas;
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_SELLER = 1;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_SELLER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                View titleView = LayoutInflater.from(mContext).inflate(R.layout.item_title, null);
                TitleHolder titleHolder = new TitleHolder(titleView);
                return titleHolder;
            case TYPE_SELLER:
                View sellerView = LayoutInflater.from(mContext).inflate(R.layout.item_seller, null);
                SellerHolder homeItemHolder = new SellerHolder(sellerView);
                return homeItemHolder;
            default:
                Log.d(TAG, "onCreateViewHolder: 还有第三种View。。。");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.setData("我是titleView---------------------");
                break;
            case TYPE_SELLER:
                SellerHolder sellerHolder = (SellerHolder) holder;
                sellerHolder.setData(mDatas.get(position));
                break;
        }
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

    class TitleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.slider)
        SliderLayout mSliderLayout;

        public TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            for (String desc : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                textSliderView
                        .description(desc)
                        .image(url_maps.get(desc));
                mSliderLayout.addSlider(textSliderView);
            }
        }
    }

    class SellerHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.seller_logo)
        ImageView mSellerLogo;
        @Bind(R.id.tvCount)
        TextView mTvCount;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.ratingBar)
        RatingBar mRatingBar;
        @Bind(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @Bind(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @Bind(R.id.tv_home_distance)
        TextView mTvHomeDistance;

        public SellerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {

        }
    }

}
