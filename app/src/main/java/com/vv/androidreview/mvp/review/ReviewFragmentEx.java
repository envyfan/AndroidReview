package com.vv.androidreview.mvp.review;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshContract;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshFragment;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.repository.ContentRepository;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;

import java.util.List;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewFragmentEx extends PullToRefreshFragment {

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
    public void onDataRefresh() {
        ReviewDocDataSource docDataSource = new ContentRepository(getActivity());
        docDataSource.getPoints(new OnLoadDataCallBack<List<Point>>() {
            @Override
            public void onSuccess(List<Point> data) {
                Logger.d(data.size() + "");
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                Logger.d("onFail");
            }
        }, true);
    }
}
