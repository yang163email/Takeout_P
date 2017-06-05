package com.yan.takeout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

        initBottomBar();
        refreshUiByIndex(0);
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
    }

    /**递归调用，使容器内所有空间都设置enable*/
    private void setEnabled(View child, boolean isEnable) {
        child.setEnabled(isEnable);

        if(child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            int childCount = ((ViewGroup) child).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) child).getChildAt(i);
                setEnabled(childAt, isEnable);
            }
        }
    }
}
