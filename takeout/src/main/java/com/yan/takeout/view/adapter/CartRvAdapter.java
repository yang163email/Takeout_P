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
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.util.PriceFormater;
import com.yan.takeout.view.activity.BusinessActivity;
import com.yan.takeout.view.fragment.GoodsFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class CartRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsInfo> mCartList;
    private GoodsFragment mGoodsFragment;

    public void setCartList(List<GoodsInfo> cartList) {
        mCartList = cartList;
        notifyDataSetChanged();
    }

    public CartRvAdapter(Context context, GoodsFragment goodsFragment) {
        mContext = context;
        mGoodsFragment = goodsFragment;
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
        viewHolder.setData(mCartList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mCartList != null) {
            return mCartList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
        private GoodsInfo mGoodsInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_minus, R.id.ib_add})
        public void onAddOrMinusClick(View view) {
            boolean isAdd = false;
            switch (view.getId()) {
                case R.id.ib_minus:
                    //减去货物
                    break;
                case R.id.ib_add:
                    //添加
                    isAdd = true;
                    doAddOperation();
                    break;
            }

            //刷新左侧红点数量
            processRedCount(isAdd);

            //刷新右侧商品数量
            mGoodsFragment.mGoodsAdapter.notifyDataSetChanged();

            //更新下方购物栏的数据
            List<GoodsInfo> cartList = mGoodsFragment.mGoodsFragmentPresenter.getCartList();
            ((BusinessActivity) mGoodsFragment.getActivity()).updateCartUi(cartList);
        }

        private void processRedCount(boolean isAdd) {
            //获取当前点击条目的typeId
            int typeId = mGoodsInfo.getTypeId();
            //根据typeId得到左侧条目位置
            int typePos = mGoodsFragment.mGoodsFragmentPresenter.getTypePosByTypeId(typeId);
            //根据左侧位置得到goodsTypeInfo对象
            GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfos.get(typePos);
            //获取其红点数
            int redCount = goodsTypeInfo.getRedCount();

            if(isAdd) {
                redCount ++;
            }else {
                redCount --;
            }
            goodsTypeInfo.setRedCount(redCount);
            //刷新数据
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
        }

        private void doAddOperation() {
            //刷新当前购物车的数量
            int count = mGoodsInfo.getCount();
            count ++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();

        }

        public void setData(GoodsInfo goodsInfo) {
            mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());
            mTvTypeAllPrice.setText(PriceFormater.format(goodsInfo.getCount() * Float.parseFloat(goodsInfo.getNewPrice())));
            mTvCount.setText(goodsInfo.getCount() + "");
        }
    }
}
