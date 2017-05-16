package com.vv.androidreview.mvp.component.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.vv.androidreview.R;

/**
 * Created by Vv
 * 2016/10/20 0020.
 * Version 1.0
 * Description：
 */

public abstract class LoadMoreHelper {
    //上拉加载的各种状态
    public static final int PULL_UP_STATE_NONE = 0;
    public static final int PULL_UP_STATE_LOADMORE = 1;
    public static final int PULL_UP_STATE_NOMORE = 2;
    public static final int PULL_UP_STATE_ERROR = 3;
    public static final int PULL_UP_STATE_NORMAL = 4;
    public static final int PULL_UP_STATE_NORMAL_CLICK = 5;

    protected int sPutUpState = PULL_UP_STATE_NONE;

    private Context mContext;
    private LoadMoreViewHelper mLoadMoreViewHelper;//底部FootView 帮助类
    private boolean mEnableLoadMore = true;//是否开启底部加载更多 //默认开启


    private View.OnClickListener mOnFootViewOnClickRefreshListener;
    private OnScrollBottomListener mOnScrollBottomListener;


    public LoadMoreHelper(Context mContext) {
        this.mContext = mContext;
    }

    /** 设置上拉加载状态
     * @param  state
     *              PULL_UP_STATE_NOMORE = 没有更多
     *              PULL_UP_STATE_ERROR = 加载失败
     *              PULL_UP_STATE_NORMAL = 正常状态
     *              PULL_UP_STATE_NORMAL_CLICK = 可点击的正常状态
     *              如果 State为null 则默认 PULL_UP_STATE_NORMAL = 正常状态
     *
     */
    public void setPullUpLoading(Integer state){
        sPutUpState = PULL_UP_STATE_NONE;
        if(state != null){
            switch (state) {
                case PULL_UP_STATE_NORMAL_CLICK:
                    mLoadMoreViewHelper.showNormalClick();
                    break;
                case PULL_UP_STATE_NOMORE:
                    mLoadMoreViewHelper.showNoMore();
                    break;
                case PULL_UP_STATE_ERROR:
                    mLoadMoreViewHelper.showFail();
                    break;
                case PULL_UP_STATE_NORMAL:
                    mLoadMoreViewHelper.showNormal();
//                        removeFootView();
                    break;
            }
        }else{
            mLoadMoreViewHelper.showNormal();
//                removeFootView();
        }
    }

    /**
     * 开启/关闭上拉加载更多功能
     * @param isOpen true 开启 false 关闭
     * @param isReAdd 是否关闭 后再次开启
     */
    public void enableLoadMore(boolean isOpen,boolean isReAdd) {
        this.mEnableLoadMore = isOpen;
        if (isOpen) {
            View footView = getFootView();
            setLoadMoreViewHelper(getLoadMoreViewHelper(footView));

            if (isReAdd) {
                setPullUpLoading(PULL_UP_STATE_NORMAL_CLICK);
            }
            addFootView();
        } else {
            sPutUpState = PULL_UP_STATE_NONE;
            removeFootView();
        }
    }

    public void loadMore(){
        if(isEnableLoadMore()){
            if(mOnScrollBottomListener != null && sPutUpState == PULL_UP_STATE_NONE){
                showLoading();
                mOnScrollBottomListener.onScrollBottom();
            }
        }
    }

    public void clickLoadMore(OnClickLoadMore onClickLoadMore){
        if(sPutUpState == PULL_UP_STATE_NONE){
            showLoading();
            if(onClickLoadMore != null){
                onClickLoadMore.todo();
            }
        }
    }

    private void showLoading(){
        sPutUpState = PULL_UP_STATE_LOADMORE;
        mLoadMoreViewHelper.showLoading();
    }

    public abstract void removeFootView();

    public abstract void addFootView();

    public boolean isEnableLoadMore() {
        return mEnableLoadMore;
    }

    public View getFootView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.loadmore_default_footer,null);
    }

    private LoadMoreViewHelper getLoadMoreViewHelper(View footView) {
        return new LoadMoreViewHelper(footView, getOnFootViewOnClickRefreshListener());
    }

    public void setOnScrollBottomListener(OnScrollBottomListener mOnScrollBottomListener) {
        this.mOnScrollBottomListener = mOnScrollBottomListener;
    }
    public void setFootViewOnClickRefreshListener(View.OnClickListener mOnFootViewOnClickRefreshListener) {
        this.mOnFootViewOnClickRefreshListener = mOnFootViewOnClickRefreshListener;
    }

    public View.OnClickListener getOnFootViewOnClickRefreshListener() {
        return mOnFootViewOnClickRefreshListener;
    }

    public OnScrollBottomListener getOnScrollBottomListener() {
        return mOnScrollBottomListener;
    }

    public interface OnScrollBottomListener{
        void onScrollBottom();
    }

    public int getFootViewState() {
        return sPutUpState;
    }

    public Context getContext() {
        return mContext;
    }

    public LoadMoreViewHelper getLoadMoreViewHelper() {
        return mLoadMoreViewHelper;
    }

    public void setLoadMoreViewHelper(LoadMoreViewHelper mLoadMoreViewHelper) {
        this.mLoadMoreViewHelper = mLoadMoreViewHelper;
    }

    public interface OnClickLoadMore{
        void todo();
    }
}
