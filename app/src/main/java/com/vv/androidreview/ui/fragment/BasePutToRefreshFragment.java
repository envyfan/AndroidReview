/*
 * Copyright (c) 2016. Vv <envyfan@qq.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vv.androidreview.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseFragment;
import com.vv.androidreview.ui.view.LoadingLayout;
import com.vv.androidreview.utils.TDevice;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 * 所有下拉刷新List Fragment的基类
 */
public abstract class BasePutToRefreshFragment<T extends BaseAdapter> extends BaseFragment {
    //下拉刷新
    public static final int REFRESH_TYPE_PULL = 0;
    //上拉加载更多
    public static final int REFRESH_TYPE_LOAD_MORE = 1;
    //每页的item最大数量
    public static final int PAGE_LIMIT = 15;

    //上拉加载的各种状态
    public static final int PULL_UP_STATE_NONE = 0;
    public static final int PULL_UP_STATE_LOADMORE = 1;
    public static final int PULL_UP_STATE_NOMORE = 2;
    public static final int PULL_UP_STATE_EEROR = 3;
    protected int sPutUpState = PULL_UP_STATE_NONE;
    //分页--目前的页码
    protected int mCurrentPageCount = 0;
    //FootView的进度圈
    protected ProgressBar mFootViewProgressBar;
    //FootViewTextView
    protected TextView mFootViewText;

    protected View mRootView;
    protected PtrFrameLayout mPtrFrameLayout;
    protected ListView mListView;
    protected T mAdapter;
    protected LoadingLayout mLoadingLayout;
    protected LinearLayout mFooterView;

    //list的数量是否满屏
    private boolean isItemfullScreen = false;

    //继承的Fragment是否支持上拉加载更多
    private boolean isLoadMore = false;

    //返回根布局的View
    public abstract View getRootView();

    //返回Adapter的实现
    public abstract T getAdapter();

    //处理读缓存逻辑
    public abstract void readCache();

    //从网络请求数据 actionType 表示 上拉类型 还是下拉类型 操作
    public abstract void requestDataByNet(int actionType);

    //初始化参数
    public abstract void initArguments();

    //创建View或者加入事件监听
    public abstract void createViewsOrListener();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = getRootView();
            initArguments();
            creatBaseViews();
            createViewsOrListener();

        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;


    }
    //创建 listView loadingView 并加入事件处理
    private void creatBaseViews() {
        initDoRefreshView();
        mListView = (ListView) mRootView.findViewById(R.id.listview);
        mLoadingLayout = (LoadingLayout) mRootView.findViewById(R.id.ly_loading);
        mLoadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPtrFrameLayout.autoRefresh(true);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (isItemfullScreen) {
                            if(sPutUpState == PULL_UP_STATE_NONE &&isLoadMore) {
                                Logger.e("list is load more data");
                                pullUpLoadData();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //firstVisibleItem -屏幕上显示的第一个item
                //visibleItemCount -屏幕上一共显示item的数目
                //totalItemCount -目前listview 一共有多少个itme
                if(totalItemCount>visibleItemCount){
                    isItemfullScreen = true;
                }else{
                    isItemfullScreen = false;
                }

            }
        });
        mAdapter = getAdapter();
        readCache();
        mListView.setAdapter(mAdapter);
    }

    public void pullUpLoadData() {
        sPutUpState = PULL_UP_STATE_LOADMORE;
        showFootView();
        requestDataByNet(REFRESH_TYPE_LOAD_MORE);
    }

    public void showFootView() {
        if (mFooterView == null) {
            mFooterView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_footer, null);
            mFootViewProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progressbar);
            mFootViewText = (TextView) mFooterView.findViewById(R.id.text);
        }
        updateFooterViewStateText();
        if (mListView.getFooterViewsCount() == 0) {
            mListView.addFooterView(mFooterView);
        }
    }

    public void removeFootView() {
        if (mFooterView != null) {
            mListView.removeFooterView(mFooterView);
        }
        sPutUpState = PULL_UP_STATE_NONE;
    }

    /**
     * 更新当前FootView的文本信息
     */
    public void updateFooterViewStateText() {
        switch (sPutUpState) {
            case PULL_UP_STATE_LOADMORE:
                mFootViewProgressBar.setVisibility(View.VISIBLE);
                mFootViewText.setText(R.string.loading);
                break;
            case PULL_UP_STATE_NOMORE:
                mFootViewProgressBar.setVisibility(View.GONE);
                mFootViewText.setVisibility(View.VISIBLE);
                mFootViewText.setText(R.string.all_load);
                break;
            case PULL_UP_STATE_EEROR:
                mFootViewProgressBar.setVisibility(View.GONE);
                mFootViewText.setVisibility(View.VISIBLE);
                mFootViewText.setText(R.string.error_load);
                break;
        }
    }

    private void initDoRefreshView() {
        mPtrFrameLayout = (PtrFrameLayout) mRootView.findViewById(R.id.layout_refresh);
        // header
        MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, (int) TDevice.dpToPixel(15), 0, (int) TDevice.dpToPixel(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setPinContent(true);

        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                Logger.e("list is load data by net");
                if (frame.isAutoRefresh()) {
                    if (mPtrFrameLayout.isRefreshing()) {
                        mPtrFrameLayout.refreshComplete();
                    }
                    mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
                }
                sPutUpState = PULL_UP_STATE_NONE;
                // 这里做下拉刷新操作
                requestDataByNet(REFRESH_TYPE_PULL);
            }
        });

    }

    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPtrFrameLayout.clearAnimation();
    }
}
