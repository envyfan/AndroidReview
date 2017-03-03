package com.vv.androidreview.mvp.component.pulltorefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;
import com.vv.androidreview.mvp.system.CodeConfig;
import com.vv.androidreview.ui.view.LoadingLayout;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public abstract class PullToRefreshFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private LoadingLayout mLoadingLayout;

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initRefreshLayout();
        initLoadingLayout();
    }

    private void initLoadingLayout() {
        View rootView = getRootView();

        if (rootView != null) {
            mLoadingLayout = (LoadingLayout) rootView.findViewById(getLoadingLayoutId());
            View view = mLoadingLayout.findViewById(R.id.img_refresh);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logger.e("onclick");
                    mLoadingLayout.setLoadingLayout(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_LOADING);
                    onLoadData();
                }
            });
        }
    }

    private void initRefreshLayout() {
        View rootView = getRootView();

        if (rootView != null) {
            mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(getRefreshLayoutId());
        }

        if (mRefreshLayout != null) {

            mRefreshLayout.setColorSchemeResources(R.color.theme_color);

            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onPullToRefresh();
                }
            });
        }
    }

    public LoadingLayout getLoadingLayout() {
        return mLoadingLayout;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public abstract int getRefreshLayoutId();

    public abstract int getLoadingLayoutId();

    public abstract void onPullToRefresh();

    public abstract void onLoadData();
}
