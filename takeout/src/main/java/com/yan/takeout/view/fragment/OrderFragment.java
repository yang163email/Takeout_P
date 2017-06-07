package com.yan.takeout.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yan.takeout.R;
import com.yan.takeout.model.net.Order;
import com.yan.takeout.presenter.OrderFragmentPresenter;
import com.yan.takeout.util.TakeoutApp;
import com.yan.takeout.view.adapter.OrderRvAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class OrderFragment extends Fragment {

    @Bind(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @Bind(R.id.srl_order)
    SwipeRefreshLayout mSrlOrder;
    private OrderFragmentPresenter mOrderFragmentPresenter;
    private OrderRvAdapter mOrderRvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order, null);

        ButterKnife.bind(this, rootView);
        mOrderFragmentPresenter = new OrderFragmentPresenter(this);

        mRvOrderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderRvAdapter = new OrderRvAdapter(getActivity());
        mRvOrderList.setAdapter(mOrderRvAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int id = TakeoutApp.sUser.getId();
        if(id == -1){
            //没有登录
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            //请求数据
            mOrderFragmentPresenter.getOrderList(id + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onLoadOrderSuccess(List<Order> orderList) {
        mOrderRvAdapter.setOrderList(orderList);
    }
}
