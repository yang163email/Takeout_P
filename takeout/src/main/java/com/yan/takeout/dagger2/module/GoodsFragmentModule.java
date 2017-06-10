package com.yan.takeout.dagger2.module;

import com.yan.takeout.presenter.GoodsFragmentPresenter;
import com.yan.takeout.view.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 楠GG on 2017/6/10.
 */

@Module
public class GoodsFragmentModule {

    GoodsFragment mGoodsFragment;

    public GoodsFragmentModule(GoodsFragment goodsFragment) {
        mGoodsFragment = goodsFragment;
    }

    @Provides
    GoodsFragmentPresenter providerGoodsFragmentPresenter() {
        return new GoodsFragmentPresenter(mGoodsFragment);
    }
}
