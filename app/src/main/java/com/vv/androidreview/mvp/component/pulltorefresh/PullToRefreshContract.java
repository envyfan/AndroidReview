package com.vv.androidreview.mvp.component.pulltorefresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vv.androidreview.mvp.base.BasePresenter;
import com.vv.androidreview.mvp.base.BaseRefreshableView;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public interface PullToRefreshContract {

    interface RefreshableView extends BaseRefreshableView<PullToRefreshContract.Presenter, SwipeRefreshLayout> {
        void setRefreshPresenter(PullToRefreshContract.Presenter refreshPresenter);
    }

    interface Presenter extends BasePresenter {
        void onRefresh();
    }
}
