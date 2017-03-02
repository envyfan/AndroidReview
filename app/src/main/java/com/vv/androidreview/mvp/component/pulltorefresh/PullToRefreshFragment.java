package com.vv.androidreview.mvp.component.pulltorefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public abstract class PullToRefreshFragment extends BaseFragment {

    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        View rootView = getRootView();

        if (rootView != null) {
            refreshLayout = (SwipeRefreshLayout) rootView.findViewById(getRefreshLayoutId());
        }

        if (refreshLayout != null) {

            refreshLayout.setColorSchemeResources(R.color.theme_color);

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onDataRefresh();
                }
            });
        }
    }

    public SwipeRefreshLayout getRefreshView() {
        return refreshLayout;
    }

    public abstract int getRefreshLayoutId();

    public abstract void onDataRefresh();
}
