package com.vv.androidreview.mvp.review.list;

import android.content.Context;

import com.vv.androidreview.mvp.base.RecycleRefreshableView;
import com.vv.androidreview.mvp.base.RefreshableView;
import com.vv.androidreview.mvp.config.CodeConfig;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.repository.ContentRepository;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.mvp.review.ReviewContract;
import com.vv.androidreview.mvp.tools.RequestDataUICallBackHelper;

import java.util.List;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewContentListPresenter implements ReviewContract.ReviewPresenter {

    private ReviewDocDataSource mReviewDocDataSource;
    private ReviewContract.ReviewListView mReviewListView;
    private RefreshableView mRefreshableView;
    private RecycleRefreshableView<ReviewContentListAdapter> mRecycleRefreshableView;

    private Point mPoint;


    public ReviewContentListPresenter(Context context,
                                      ReviewContract.ReviewListView reviewView,
                                      RefreshableView refreshableView,
                                      RecycleRefreshableView<ReviewContentListAdapter> recycleRefreshableView,
                                      Point point) {
        mReviewDocDataSource = new ContentRepository(context);
        this.mReviewListView = reviewView;
        this.mRefreshableView = refreshableView;
        this.mRecycleRefreshableView = recycleRefreshableView;
        this.mPoint = point;
    }

    @Override
    public void requestData(boolean isReadCache, final boolean isAutoRequest) {
        mReviewDocDataSource.getContents(new OnLoadDataCallBack<List<Content>>() {
            @Override
            public void onSuccess(List<Content> data) {
                if (data != null && data.size() > 0) {
                    mRecycleRefreshableView.getAdapter().setData(data);
                    RequestDataUICallBackHelper.normalRequestSuccess(isAutoRequest,mRefreshableView);
                }else{
                    mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_REFRESH);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                RequestDataUICallBackHelper.normalRequestFail(isAutoRequest, mRefreshableView, errorMsg);
            }
        },mPoint,isReadCache);
    }
}
