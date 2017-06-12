package com.yan.takeout.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.yan.takeout.R;
import com.yan.takeout.model.net.GoodsInfo;
import com.yan.takeout.model.net.GoodsTypeInfo;
import com.yan.takeout.model.net.Seller;
import com.yan.takeout.util.PriceFormater;
import com.yan.takeout.util.TakeoutApp;
import com.yan.takeout.view.adapter.BusinessFragmentPagerAdapter;
import com.yan.takeout.view.adapter.CartRvAdapter;
import com.yan.takeout.view.fragment.CommentFragment;
import com.yan.takeout.view.fragment.GoodsFragment;
import com.yan.takeout.view.fragment.SellerFragment;

import java.io.Serializable;
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
    View mBottomSheetView;

    private List<Fragment> mFragmentList = new ArrayList<>();
    public Seller mSeller;
    private RecyclerView mRvCart;
    private CartRvAdapter mCartRvAdapter;
    public boolean mHasSelectInfo;

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

    /**处理传递的intent*/
    private void processIntent() {
        if(getIntent() != null) {
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
            mTvSendPrice.setText(PriceFormater.format(Float.parseFloat(mSeller.getSendPrice())) + "元起送");
            mTvDeliveryFee.setText("另需起送费" + PriceFormater.format(Float.parseFloat(mSeller.getDeliveryFee())));
            mHasSelectInfo = getIntent().getBooleanExtra("hasSelectInfo", false);
        }
    }

    private void initFragments() {
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CommentFragment());
        mFragmentList.add(new SellerFragment());
    }

    @OnClick({R.id.ib_back, R.id.tvSubmit, R.id.bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tvSubmit:
                //结算
                Intent intent = new Intent(this, ConfirmOrderActivity.class);
                GoodsFragment goodsFragment = (GoodsFragment) mFragmentList.get(0);
                List<GoodsInfo> cartList = goodsFragment.mGoodsFragmentPresenter.getCartList();
                intent.putExtra("cartList", (Serializable) cartList);
                intent.putExtra("seller", mSeller);
                startActivity(intent);
                break;

            case R.id.bottom:
                //弹dialog
                showOrHideCart();
                break;
        }
    }

    public void showOrHideCart() {
        GoodsFragment goodsFragment = (GoodsFragment) mFragmentList.get(0);
        if(mBottomSheetView == null) {
            mBottomSheetView = LayoutInflater.from(this).inflate(R.layout.cart_list, null);
            TextView tvClearCart = (TextView) mBottomSheetView.findViewById(R.id.tvClear);
            tvClearCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showClearCartDialog();
                }
            });

            mRvCart = (RecyclerView) mBottomSheetView.findViewById(R.id.rvCart);
            mRvCart.setLayoutManager(new LinearLayoutManager(this));
            mCartRvAdapter = new CartRvAdapter(this, goodsFragment);
            mRvCart.setAdapter(mCartRvAdapter);
        }

        if(mBottomSheetLayout.isSheetShowing()) {
            //关闭显示
            mBottomSheetLayout.dismissSheet();
        }else {
            //首先拿到购物车数据
            List<GoodsInfo> cartList = goodsFragment.mGoodsFragmentPresenter.getCartList();
            mCartRvAdapter.setCartList(cartList);
            if(cartList != null && cartList.size() > 0) {
                //有数据才显示
                mBottomSheetLayout.showWithSheetView(mBottomSheetView);
            }
        }
    }

    /**弹出清空购物车的dialog*/
    private void showClearCartDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("确定不吃了么？");
        dialog.setPositiveButton("是，我要减肥", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearCart();
            }
        });
        dialog.setNegativeButton("不，还要吃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    /**清空购物车*/
    private void clearCart() {
        //四个地方需要清空
        //当前购物车中
        GoodsFragment goodsFragment = (GoodsFragment) mFragmentList.get(0);
        goodsFragment.mGoodsFragmentPresenter.clearCart();
        mCartRvAdapter.notifyDataSetChanged();
        showOrHideCart();

        //左侧红点清空
        List<GoodsTypeInfo> goodsTypeInfoList = goodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfos;
        for (GoodsTypeInfo goodsTypeInfo : goodsTypeInfoList) {
        	goodsTypeInfo.setRedCount(0);
        }
        goodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();

        //右侧清空
        goodsFragment.mGoodsAdapter.notifyDataSetChanged();

        //底部清空
        List<GoodsInfo> cartList = goodsFragment.mGoodsFragmentPresenter.getCartList();
        updateCartUi(cartList);

        //清除缓存
        TakeoutApp.sInstance.clearCacheSelectedInfo((int) mSeller.getId());
    }

    public void addImageButton(ImageView imageView, int width, int height) {
        mFlContainer.addView(imageView, width, height);
    }

    public void getImgCartLocation(int[] destLocation) {
        mImgCart.getLocationInWindow(destLocation);
    }

    /**处理购物车的显示*/
    public void updateCartUi(List<GoodsInfo> cartList) {
        int cartCount = 0;     //购物车商品总数
        float countPrice = 0;   //购物车商品总价
        if(cartList != null) {
            for (GoodsInfo goodsInfo : cartList) {
            	cartCount += goodsInfo.getCount();
                countPrice += goodsInfo.getCount() * Float.parseFloat(goodsInfo.getNewPrice());
            }
        }

        if(cartCount > 0) {
            mTvSelectNum.setVisibility(View.VISIBLE);
            mTvSelectNum.setText(cartCount + "");
        }else {
            mTvSelectNum.setVisibility(View.GONE);
        }
        mTvCountPrice.setText(PriceFormater.format(countPrice));

        if(countPrice > Float.parseFloat(mSeller.getSendPrice())){
            mTvSendPrice.setVisibility(View.GONE);
            mTvSubmit.setVisibility(View.VISIBLE);
        }else {
            mTvSendPrice.setVisibility(View.VISIBLE);
            mTvSubmit.setVisibility(View.GONE);
        }
    }
}
