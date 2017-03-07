package com.vv.androidreview.mvp.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshFragment;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewFragmentEx extends PullToRefreshFragment implements ReviewContract.ReviewView {

    private ReviewContract.ReviewPresenter mReviewPresenter;

    public static ReviewFragmentEx newInstance() {

        Bundle args = new Bundle();

        ReviewFragmentEx fragment = new ReviewFragmentEx();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateRootView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_layout;
    }

    @Override
    public int getRefreshLayoutId() {
        return R.id.test_refresh;
    }

    @Override
    public int getLoadingLayoutId() {
        return R.id.ly_loading;
    }

    @Override
    public void onPullToRefresh() {
        mReviewPresenter.requestPoint(false, false);
    }

    @Override
    public void onLoadData() {
        mReviewPresenter.requestPoint(true, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoadData();
    }

    @Override
    public void completeDataLoading(int loadingLayoutStatusType) {
        getLoadingLayout().setLoadingLayout(loadingLayoutStatusType);
    }


    @Override
    public void completePullToRefresh(String msg) {
        getRefreshLayout().setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ReviewContract.ReviewPresenter presenter) {
        this.mReviewPresenter = presenter;
    }
}
