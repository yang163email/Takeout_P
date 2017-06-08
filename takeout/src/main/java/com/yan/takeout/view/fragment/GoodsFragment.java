package com.yan.takeout.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.presenter.GoodsFragmentPresenter;
import com.yan.takeout.view.activity.BusinessActivity;
import com.yan.takeout.view.adapter.GoodsAdapter;
import com.yan.takeout.view.adapter.GoodsTypeRvAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by 楠GG on 2017/6/8.
 */

public class GoodsFragment extends Fragment {
    @Bind(R.id.rv_goods_type)
    RecyclerView mRvGoodsType;
    @Bind(R.id.slhlv)
    public StickyListHeadersListView mSlhlv;

    public GoodsTypeRvAdapter mGoodsTypeRvAdapter;
    public GoodsFragmentPresenter mGoodsFragmentPresenter;
    private GoodsAdapter mGoodsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View goodsView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods, container, false);
        ButterKnife.bind(this, goodsView);


        mGoodsFragmentPresenter = new GoodsFragmentPresenter(this);
        //左侧滚动栏
        mRvGoodsType.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoodsTypeRvAdapter = new GoodsTypeRvAdapter(getActivity());
        mGoodsTypeRvAdapter.setGoodsFragment(this);
        mRvGoodsType.setAdapter(mGoodsTypeRvAdapter);

        //右侧栏
        mGoodsAdapter = new GoodsAdapter(getActivity());
        mSlhlv.setAdapter(mGoodsAdapter);
        return goodsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        long sellerId = ((BusinessActivity) getActivity()).mSeller.getId();
        mGoodsFragmentPresenter.getBusinessInfo((int) sellerId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onGoodsTypeSuccess(List<GoodsTypeInfo> goodsTypeInfos) {
        mGoodsTypeRvAdapter.setGoodsTypeInfoList(goodsTypeInfos);
    }

    public void onAllGoodsSuccess(List<GoodsInfo> allGoodsTypeInfo) {
        mGoodsAdapter.setGoodsInfoList(allGoodsTypeInfo);
    }
}
