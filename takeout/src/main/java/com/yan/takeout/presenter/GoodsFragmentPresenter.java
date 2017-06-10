package com.yan.takeout.presenter;

import android.widget.AbsListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.util.TakeoutApp;
import com.yan.takeout.view.activity.BusinessActivity;
import com.yan.takeout.view.fragment.GoodsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/8.
 */

public class GoodsFragmentPresenter extends NetPresenter {
    private GoodsFragment mGoodsFragment;
    private List<GoodsInfo> mAllGoodsTypeInfo;
    public List<GoodsTypeInfo> mGoodsTypeInfos;

    public GoodsFragmentPresenter(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
    }

    public void getBusinessInfo(int sellerId) {
        Call<ResponseInfo> businessInfoCall = mTakeoutService.getBusinessInfo(sellerId);
        businessInfoCall.enqueue(mCallback);
    }
    @Override
    protected void onConnectError(String message) {

    }

    @Override
    protected void onServerBug(int code) {

    }

    @Override
    protected void onSuccess(String jsonData) {
        mAllGoodsTypeInfo = new ArrayList<>();
        //解析数据
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String listStr = jsonObject.getString("list");

            mGoodsTypeInfos = gson.fromJson(listStr, new TypeToken<List<GoodsTypeInfo>>(){}.getType());
            mGoodsFragment.onGoodsTypeSuccess(mGoodsTypeInfos);

            for (int i = 0; i < mGoodsTypeInfos.size(); i++) {
                GoodsTypeInfo goodsTypeInfo = mGoodsTypeInfos.get(i);

                //查找此类别有多少缓存数据
                int redCount = 0;
                boolean hasSelectInfo = ((BusinessActivity) mGoodsFragment.getActivity()).mHasSelectInfo;
                if(hasSelectInfo) {
                    redCount = TakeoutApp.sInstance.queryCacheSelectedInfoByTypeId(goodsTypeInfo.getId());
                    goodsTypeInfo.setRedCount(redCount);
                }

                List<GoodsInfo> goodsTypeInfoList = goodsTypeInfo.getList();
                for (int j = 0; j < goodsTypeInfoList.size(); j++) {
                    GoodsInfo goodsInfo = goodsTypeInfoList.get(j);

                    //查找商品有多少缓存
                    if(redCount > 0) {
                        int count = TakeoutApp.sInstance.queryCacheSelectedInfoByGoodsId(goodsInfo.getId());
                        goodsInfo.setCount(count);
                    }

                    //将类型id、名称设置进去
                    goodsInfo.setTypeId(goodsTypeInfo.getId());
                    goodsInfo.setTypeName(goodsTypeInfo.getName());
                    mAllGoodsTypeInfo.add(goodsInfo);
                }
            }
            mGoodsFragment.onAllGoodsSuccess(mAllGoodsTypeInfo);

            //获取数据成功后刷新下方数据
            ((BusinessActivity) mGoodsFragment.getActivity()).updateCartUi(getCartList());

            mGoodsFragment.mSlhlv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //拿到滚动之前左侧选中的位置
                    int oldPos = mGoodsFragment.mGoodsTypeRvAdapter.mSelectedPosition;
                    //根据第一条可见item获取id
                    int newTypeId = mAllGoodsTypeInfo.get(firstVisibleItem).getTypeId();
                    int newPos = getTypePosByTypeId(newTypeId);

                    if(oldPos != newPos) {
                        //切换了类别
                        mGoodsFragment.mGoodsTypeRvAdapter.mSelectedPosition = newPos;
                        mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**循环左侧列表*/
    public int getTypePosByTypeId(int newTypeId) {
        int position = 0;
        for (int i = 0; i < mGoodsTypeInfos.size(); i++) {
            GoodsTypeInfo goodsTypeInfo = mGoodsTypeInfos.get(i);
            int id = goodsTypeInfo.getId();
            if(id == newTypeId) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**通过typeId拿到商品的位置*/
    public int getGoodsPosByTypeId(int typeId) {
        int position = 0;
        for (int i = 0; i < mAllGoodsTypeInfo.size(); i++) {
            GoodsInfo goodsInfo = mAllGoodsTypeInfo.get(i);
            int goodsTypeId = goodsInfo.getTypeId();
            if(goodsTypeId == typeId) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**获取购物车的goods集合*/
    public List<GoodsInfo> getCartList() {
        List<GoodsInfo> cartList = new ArrayList<>();

        if(mAllGoodsTypeInfo != null) {
            for (GoodsInfo goodsInfo : mAllGoodsTypeInfo) {
            	if(goodsInfo.getCount() > 0) {
                    //有点餐数量
                    cartList.add(goodsInfo);
                }
            }
        }
        return cartList;
    }

    /**清空购物车*/
    public void clearCart() {
        if(mAllGoodsTypeInfo != null) {
            for (GoodsInfo goodsInfo : mAllGoodsTypeInfo) {
                goodsInfo.setCount(0);
            }
        }
    }
}
