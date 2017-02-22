package com.vv.androidreview.mvp.component.pulltorefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public abstract class PullToRefreshFragment extends BaseFragment implements PullToRefreshContract.RefreshableView {

    private SwipeRefreshLayout refreshLayout;

    private PullToRefreshContract.Presenter refreshPresenter;

    @Override
    public SwipeRefreshLayout getRefreshView() {
        return refreshLayout;
    }

    public void initRefreshLayout(int resId) {
        View rootView = getRootView();

        if (rootView != null) {
            refreshLayout = (SwipeRefreshLayout) rootView.findViewById(resId);
        }

        if (refreshLayout != null) {

            refreshLayout.setColorSchemeResources(R.color.theme_color);

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshPresenter.onRefresh();
                }
            });
        }
    }

    @Override
    public void setRefreshPresenter(PullToRefreshContract.Presenter presenter) {
        this.refreshPresenter = presenter;
    }
}
