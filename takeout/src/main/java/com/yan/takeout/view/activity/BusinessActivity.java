package com.yan.takeout.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.yan.takeout.R;
import com.yan.takeout.model.net.Seller;
import com.yan.takeout.view.adapter.BusinessFragmentPagerAdapter;
import com.yan.takeout.view.fragment.CommentFragment;
import com.yan.takeout.view.fragment.GoodsFragment;
import com.yan.takeout.view.fragment.SellerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 楠GG on 2017/6/8.
 */

public class BusinessActivity extends Activity {
    @Bind(R.id.ib_back)
    ImageButton mIbBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.ib_menu)
    ImageButton mIbMenu;
    @Bind(R.id.vp)
    ViewPager mVp;
    @Bind(R.id.bottomSheetLayout)
    BottomSheetLayout mBottomSheetLayout;
    @Bind(R.id.imgCart)
    ImageView mImgCart;
    @Bind(R.id.tvSelectNum)
    TextView mTvSelectNum;
    @Bind(R.id.tvCountPrice)
    TextView mTvCountPrice;
    @Bind(R.id.tvSendPrice)
    TextView mTvSendPrice;
    @Bind(R.id.tvDeliveryFee)
    TextView mTvDeliveryFee;
    @Bind(R.id.tvSubmit)
    TextView mTvSubmit;
    @Bind(R.id.bottom)
    LinearLayout mBottom;
    @Bind(R.id.fl_Container)
    FrameLayout mFlContainer;
    @Bind(R.id.tabs)
    TabLayout mTabs;

    private List<Fragment> mFragmentList = new ArrayList<>();
    public Seller mSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);

        processIntent();
        initFragments();
        BusinessFragmentPagerAdapter businessFragmentPagerAdapter =
                new BusinessFragmentPagerAdapter(getFragmentManager());
        businessFragmentPagerAdapter.setFragmentList(mFragmentList);
        mVp.setAdapter(businessFragmentPagerAdapter);

        //绑定指示器与ViewPager
        mTabs.setupWithViewPager(mVp);
    }

    private void processIntent() {
        if(getIntent() != null) {
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
        }
    }

    private void initFragments() {
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CommentFragment());
        mFragmentList.add(new SellerFragment());
    }

    @OnClick({R.id.ib_back, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tvSubmit:
                //结算
                break;
        }
    }

    public void addImageButton(ImageView imageView, int width, int height) {
        mFlContainer.addView(imageView, width, height);
    }

    public void getImgCartLocation(int[] destLocation) {
        mImgCart.getLocationInWindow(destLocation);
    }
}
