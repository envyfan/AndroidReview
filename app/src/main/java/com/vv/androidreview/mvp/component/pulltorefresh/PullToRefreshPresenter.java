package com.vv.androidreview.mvp.component.pulltorefresh;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class PullToRefreshPresenter implements PullToRefreshContract.Presenter {

    private PullToRefreshContract.RefreshableView refreshableView;

    public PullToRefreshPresenter(PullToRefreshContract.RefreshableView refreshableView) {
        this.refreshableView = refreshableView;
    }

    @Override
    public void onRefresh() {
        // TODO:  model to get Data
    }
}
