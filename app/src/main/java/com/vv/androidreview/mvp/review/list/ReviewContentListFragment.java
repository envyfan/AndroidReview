package com.vv.androidreview.mvp.review.list;

import android.os.Bundle;

import com.vv.androidreview.mvp.component.pulltorefresh.RecyclePullToRefreshFragment;
import com.vv.androidreview.mvp.review.ReviewContract;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewContentListFragment extends RecyclePullToRefreshFragment<ReviewContentListAdapter> implements ReviewContract.ReviewListView{

    private ReviewContract.ReviewPresenter mReviewPresenter;

    private ReviewContentListAdapter mAdapter;

    public static ReviewContentListFragment newInstance() {
        Bundle args = new Bundle();
        ReviewContentListFragment fragment = new ReviewContentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(ReviewContract.ReviewPresenter presenter) {
        this.mReviewPresenter = presenter;
    }

    @Override
    public ReviewContentListAdapter getAdapter() {
        if (mAdapter != null) {
            return mAdapter;
        }else{
            mAdapter = new ReviewContentListAdapter(getActivity());
            return mAdapter;
        }
    }

    @Override
    public void onPullToRefresh() {
        mReviewPresenter.requestData(false,false);
    }

    @Override
    public void onLoadData() {
        mReviewPresenter.requestData(true,true);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoadData();
    }
}
