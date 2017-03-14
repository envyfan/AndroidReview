package com.vv.androidreview.mvp.component.pulltorefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;
import com.vv.androidreview.mvp.base.RefreshableView;
import com.vv.androidreview.mvp.config.CodeConfig;
import com.vv.androidreview.mvp.system.StaticValues;
import com.vv.androidreview.ui.view.LoadingLayout;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public abstract class PullToRefreshFragment extends BaseFragment implements RefreshableView {

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
                    Logger.d("loadingView onclick");
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

    @Override
    public void completeDataLoading(int loadingLayoutStatusType) {
        mLoadingLayout.setLoadingLayout(loadingLayoutStatusType);
    }

    @Override
    public void completePullToRefresh(String msg) {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void completePullToRefresh() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), StaticValues.REQUEST_SUCCESS, Toast.LENGTH_SHORT).show();
    }

    public abstract int getRefreshLayoutId();

    public abstract int getLoadingLayoutId();

    public abstract void onPullToRefresh();

    public abstract void onLoadData();
}
