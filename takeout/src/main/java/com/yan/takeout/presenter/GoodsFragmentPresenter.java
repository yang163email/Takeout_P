package com.yan.takeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.model.net.ResponseInfo;
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

            List<GoodsTypeInfo> goodsTypeInfos = gson.fromJson(listStr, new TypeToken<List<GoodsTypeInfo>>(){}.getType());
            mGoodsFragment.onGoodsTypeSuccess(goodsTypeInfos);

            for (int i = 0; i < goodsTypeInfos.size(); i++) {
                GoodsTypeInfo goodsTypeInfo = goodsTypeInfos.get(i);
                List<GoodsInfo> goodsTypeInfoList = goodsTypeInfo.getList();
                for (int j = 0; j < goodsTypeInfoList.size(); j++) {
                    GoodsInfo goodsInfo = goodsTypeInfoList.get(j);
                    //将类型id、名称设置进去
                    goodsInfo.setTypeId(goodsTypeInfo.getId());
                    goodsInfo.setTypeName(goodsTypeInfo.getName());
                    mAllGoodsTypeInfo.add(goodsInfo);
                }
            }
            mGoodsFragment.onAllGoodsSuccess(mAllGoodsTypeInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
