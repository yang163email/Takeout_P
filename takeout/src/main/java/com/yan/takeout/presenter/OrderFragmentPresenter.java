package com.yan.takeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.takeout.model.net.Order;
import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.view.fragment.OrderFragment;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/7.
 */

public class OrderFragmentPresenter extends NetPresenter {
    private OrderFragment mOrderFragment;

    public OrderFragmentPresenter(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    public void getOrderList(String userId) {
        Call<ResponseInfo> call = mTakeoutService.getOrderList(userId);
        call.enqueue(mCallback);
    }

    @Override
    protected void onConnectError(String message) {

    }

    @Override
    protected void onServerBug(int code) {

    }

    @Override
    protected void onSuccess(String jsonData) {
        Gson gson = new Gson();
        List<Order> orderList = gson.fromJson(jsonData, new TypeToken<List<Order>>(){}.getType());
        mOrderFragment.onLoadOrderSuccess(orderList);
    }
}
