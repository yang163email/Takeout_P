package com.yan.takeout.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.util.PriceFormater;
import com.yan.takeout.view.fragment.GoodsFragment;

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
    private GoodsFragment mGoodsFragment;

    public void setGoodsFragment(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
    }

    /**设置数据*/
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
        private static final int DURATION = 500;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_add, R.id.ib_minus})
        public void onAddOrMinusClick(View v) {
            boolean isAdd = false;
            switch (v.getId()) {
                case R.id.ib_add:
                    //处理增加商品数量
                    doAddOperation();
                    isAdd = true;
                    break;
                case R.id.ib_minus:
                    //处理减去商品数
                    doMinusOperation();
                    break;
            }
            processRedCount(isAdd);
        }

        /**处理左侧红点个数*/
        private void processRedCount(boolean isAdd) {
            //拿到点击的typeid
            int typeId = mGoodsInfo.getTypeId();
            //根据typeid遍历得到左边pos
            int pos = mGoodsFragment.mGoodsFragmentPresenter.getTypePosByTypeId(typeId);

            //根据pos找到左侧整个的goodtypeInfo
            GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfos.get(pos);
            //设置红点数量
            int redCount = goodsTypeInfo.getRedCount();
            //刷新
            if(isAdd) {
                redCount ++;
            }else {
                redCount --;
            }
            goodsTypeInfo.setRedCount(redCount);
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
        }

        private void doMinusOperation() {
            int count = mGoodsInfo.getCount();
            //只剩一个时增加动画效果
            if(count == 1) {
                AnimationSet hideAnimation = getShowOrHideAnimation(false);
                mIbMinus.startAnimation(hideAnimation);
                mTvCount.startAnimation(hideAnimation);
            }
            count --;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();
        }

        /**增加商品数*/
        private void doAddOperation() {
            int count = mGoodsInfo.getCount();
            //首次添加增加动画效果
            if(count == 0) {
                AnimationSet showAnimation = getShowOrHideAnimation(true);
                mIbMinus.startAnimation(showAnimation);
                mTvCount.startAnimation(showAnimation);
            }
            count ++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();

            //增加抛物线动画

            //拿到+号在窗体的位置
            int[] outLocation = new int[2];
            mIbAdd.getLocationInWindow(outLocation);
            Log.d(TAG, "doAddOperation: " + outLocation[0] + " " + outLocation[1]);
        }

        /**显示或隐藏动画*/
        private AnimationSet getShowOrHideAnimation(boolean isShow) {
            //创建动画集
            AnimationSet set = new AnimationSet(false);
            set.setDuration(DURATION);

            //旋转动画
            RotateAnimation rotateAnim;
            if(isShow) {
                rotateAnim = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5F);
            }else {
                rotateAnim = new RotateAnimation(720, 0,
                        Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5F);
            }
            rotateAnim.setDuration(DURATION);
            set.addAnimation(rotateAnim);

            //透明度动画
            AlphaAnimation alphaAnim;
            if(isShow) {
                alphaAnim = new AlphaAnimation(0, 1);
            }else {
                alphaAnim = new AlphaAnimation(1, 0);
            }
            alphaAnim.setDuration(DURATION);
            set.addAnimation(alphaAnim);

            //位移动画
            TranslateAnimation translateAnim;
            if(isShow) {
                translateAnim = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 2,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
            }else {
                translateAnim = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 2,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
            }
            translateAnim.setDuration(DURATION);
            set.addAnimation(translateAnim);

            return set;
        }

        public void setData(GoodsInfo goodsInfo) {
            mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());

//            Log.d(TAG, "setData: " + goodsInfo.getIcon());
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
                mIbMinus.setClickable(true);
            }else {
                //不显示
                mTvCount.setVisibility(View.INVISIBLE);
                mIbMinus.setVisibility(View.INVISIBLE);
                //防手快出现负数商品数
                mIbMinus.setClickable(false);
            }
            mTvCount.setText(count + "");
        }
    }
}
