package com.yan.takeout.view.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yan.takeout.R;
import com.yan.takeout.view.fragment.HomeFragment;
import com.yan.takeout.view.fragment.MoreFragment;
import com.yan.takeout.view.fragment.OrderFragment;
import com.yan.takeout.view.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @Bind(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initBottomBar();
        refreshUiByIndex(0);
    }

    List<Fragment> mFragments = new ArrayList<>();
    private void initFragment() {
        mFragments.add(new HomeFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new UserFragment());
        mFragments.add(new MoreFragment());
    }

    /**初始化底部按钮*/
    private void initBottomBar() {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取孩子的索引
                    int index = mMainBottomeSwitcherContainer.indexOfChild(child);
                    refreshUiByIndex(index);
                }
            });
        }

    }

    /**通过索引刷新状态*/
    private void refreshUiByIndex(int index) {
        //将底部导航栏设置点击样式
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mMainBottomeSwitcherContainer.getChildAt(i);
            if(i == index) {
                //选中
                setEnabled(child, false);
            }else {
                //未选中
                setEnabled(child, true);
            }
        }

        //切换Fragment
        getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mFragments.get(index)).commit();
    }

    /**递归调用，使容器内所有空间都设置enable*/
    private void setEnabled(View child, boolean isEnable) {
        child.setEnabled(isEnable);

        if(child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) child).getChildAt(i);
                setEnabled(childAt, isEnable);
            }
        }
    }
}
