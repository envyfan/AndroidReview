package com.vv.androidreview.mvp.component.pulltorefresh;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.RecycleRefreshableView;
import com.vv.androidreview.mvp.base.RecyclerViewHeaderFooterWrapper;

/**
 * Created by zhiwei.a.fan on 3/14/2017.
 */

public abstract class RecyclePullToRefreshFragment<T extends RecyclerView.Adapter> extends PullToRefreshFragment implements RecycleRefreshableView<T> {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private T mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_recycler_pull_to_refresh;
    }

    @Override
    public int getLoadingLayoutId() {
        return R.id.ly_loading;
    }

    @Override
    public int getRefreshLayoutId() {
        return R.id.sl_refresh;
    }

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateRootView(inflater, container, savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.ry_recyclerview);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mAdapter = getAdapter();
        mRecyclerView.setAdapter(new RecyclerViewHeaderFooterWrapper<>(mAdapter));
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null) {
            return new LinearLayoutManager(getActivity());
        } else {
            return mLayoutManager;
        }
    }

}
