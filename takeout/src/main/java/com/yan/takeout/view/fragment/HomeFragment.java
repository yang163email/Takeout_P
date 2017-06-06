package com.yan.takeout.view.fragment;

import android.animation.ArgbEvaluator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yan.takeout.R;
import com.yan.takeout.model.net.Seller;
import com.yan.takeout.presenter.HomeFragmentPresenter;
import com.yan.takeout.view.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

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
    private HomeFragmentPresenter mHomeFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);

        mHomeFragmentPresenter = new HomeFragmentPresenter(this);
        //设置布局管理器
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getActivity());
        //设置adapter
        mRvHome.setAdapter(mHomeAdapter);

        return rootView;
    }

    private int sumY;
    /**最大位移值*/
    private float distance = 250;
    private int startColor = 0x553190E8;
    private int endColor = 0xFF3190E8;
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        testData();

        mHomeFragmentPresenter.loadHomeInfo();

        //显示数据后再设置滚动事件监听
        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled: dy:" + dy);
                sumY += dy;
                int bgColor;
                if(sumY < 0) {
                    //初始颜色
                    bgColor = startColor;
                }else if(sumY > distance) {
                    //最终颜色
                    bgColor = endColor;
                }else {
                    bgColor = (int) mArgbEvaluator.evaluate(sumY / distance, startColor, endColor);
                }
                mLlTitleContainer.setBackgroundColor(bgColor);
            }
        });
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

    /**响应失败
     * @param code*/
    public void onHomeFailed(int code) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "响应失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**出错
     * @param message*/
    public void onHomeError(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "没连上网", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**成功响应*/
    public void onHomeSuccess(List<Seller> nearbySellerList, List<Seller> otherSellerList) {
        mHomeAdapter.setData(nearbySellerList, otherSellerList);
    }
}
