package com.yan.takeout.dagger2.component;

import com.yan.takeout.dagger2.module.GoodsFragmentModule;
import com.yan.takeout.view.fragment.GoodsFragment;

import dagger.Component;

/**
 * Created by 楠GG on 2017/6/10.
 */

@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentComponent {

    void in(GoodsFragment goodsFragment);
}
