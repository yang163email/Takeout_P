package com.yan.takeout.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.takeout.R;
import com.yan.takeout.view.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class HomeFragment extends Fragment {

    @Bind(R.id.rv_home)
    RecyclerView mRvHome;
    @Bind(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @Bind(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @Bind(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    private HomeAdapter mHomeAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);

        //设置布局管理器
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getActivity());
        //设置adapter
        mRvHome.setAdapter(mHomeAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        testData();
        //将数据传递过去
        mHomeAdapter.setData(mDatas);
    }

    /**使用假数据测试*/
    private void testData() {
        for (int i = 0; i < 50; i++) {
            mDatas.add("模拟数据：" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
