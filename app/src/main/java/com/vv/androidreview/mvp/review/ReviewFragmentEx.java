package com.vv.androidreview.mvp.review;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshContract;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshFragment;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewFragmentEx extends PullToRefreshFragment{

    public static ReviewFragmentEx newInstance() {
        
        Bundle args = new Bundle();
        
        ReviewFragmentEx fragment = new ReviewFragmentEx();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initRefreshLayout(R.id.test_refresh);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_layout;
    }



}
