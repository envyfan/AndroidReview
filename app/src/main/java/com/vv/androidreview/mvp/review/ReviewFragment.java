package com.vv.androidreview.mvp.review;

import android.os.Bundle;

import com.vv.androidreview.mvp.component.pulltorefresh.RecyclePullToRefreshFragment;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewFragment extends RecyclePullToRefreshFragment<ReviewListAdapter> implements ReviewContract.ReviewView {

    private ReviewContract.ReviewPresenter mReviewPresenter;

    private ReviewListAdapter mAdapter;

    public static ReviewFragment newInstance() {
        Bundle args = new Bundle();
        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ReviewListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ReviewListAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void onPullToRefresh() {
        mReviewPresenter.requestData(false, false);
    }

    @Override
    public void onLoadData() {
        mReviewPresenter.requestData(true, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoadData();
    }

    @Override
    public void setPresenter(ReviewContract.ReviewPresenter presenter) {
        this.mReviewPresenter = presenter;
    }
}
