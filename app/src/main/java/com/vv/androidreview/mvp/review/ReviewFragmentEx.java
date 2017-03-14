package com.vv.androidreview.mvp.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.RecycleRefreshableView;
import com.vv.androidreview.mvp.base.RefreshableView;
import com.vv.androidreview.mvp.component.pulltorefresh.RecyclePullToRefreshFragment;
import com.vv.androidreview.mvp.main.ReviewListAdapterGV;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewFragmentEx extends RecyclePullToRefreshFragment<ReviewListAdapterGV> implements ReviewContract.ReviewView {

    private ReviewContract.ReviewPresenter mReviewPresenter;

    private ReviewListAdapterGV mAdapter;

    public static ReviewFragmentEx newInstance() {
        Bundle args = new Bundle();
        ReviewFragmentEx fragment = new ReviewFragmentEx();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ReviewListAdapterGV getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ReviewListAdapterGV(getActivity());
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
