package com.vv.androidreview.mvp.component.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.vv.androidreview.mvp.base.RecyclerViewHeaderFooterWrapper;


/**
 * Created by Vv
 * 2016/10/12 0012.
 * Version 1.0
 * Description：
 */

public class RecycleViewLoadMoreHelper extends LoadMoreHelper {


    private RecyclerView mRecyclerView;

    private RecycleViewScrollHelper mScrollHelper; //RecycleView的滚动事件辅助类



    public RecycleViewLoadMoreHelper(RecyclerView recyclerView, Context context, OnScrollBottomListener onScrollBottomListener) {
        super(context);
        this.mRecyclerView = recyclerView;
        setOnScrollBottomListener(onScrollBottomListener);
        init();
    }

    private void init() {
        mScrollHelper = new RecycleViewScrollHelper(new RecycleViewScrollHelper.OnScrollPositionChangedListener() {
            /**
             * 滑动到顶部的回调事件
             */
            @Override
            public void onScrollToTop() {

            }

            /**
             * 滑动到底部的回调事件
             */
            @Override
            public void onScrollToBottom() {
                loadMore();
            }

            /**
             * 滑动到未知位置的回调事件
             *
             * @param isTopViewVisible    当前位置顶部第一个itemView是否可见,这里是指adapter中的最后一个itemView
             * @param isBottomViewVisible 当前位置底部最后一个itemView是否可见,这里是指adapter中的最后一个itemView
             */
            @Override
            public void onScrollToUnknown(boolean isTopViewVisible, boolean isBottomViewVisible) {

            }
        });
        //开启item是否满屏检查
        mScrollHelper.setCheckIfItemViewFullRecycleViewForBottom(true);
        //把滑动辅助类关联到RecyclerView
        mScrollHelper.attachToRecycleView(mRecyclerView);


    }

    @Override
    public void addFootView() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        RecyclerViewHeaderFooterWrapper adapterHF;
        if (adapter instanceof RecyclerViewHeaderFooterWrapper) {
            adapterHF = (RecyclerViewHeaderFooterWrapper) mRecyclerView.getAdapter();
            adapterHF.addFooter(getLoadMoreViewHelper().getFootView());
        }
    }

    @Override
    public void removeFootView() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        RecyclerViewHeaderFooterWrapper adapterHF;
        if (adapter instanceof RecyclerViewHeaderFooterWrapper) {
            adapterHF = (RecyclerViewHeaderFooterWrapper) mRecyclerView.getAdapter();
            if (adapterHF.getFootSize() != 0) {
                adapterHF.removeFooter(getLoadMoreViewHelper().getFootView());
            }
        }
    }

    public RecycleViewScrollHelper getScrollHelper() {
        return mScrollHelper;
    }
}
