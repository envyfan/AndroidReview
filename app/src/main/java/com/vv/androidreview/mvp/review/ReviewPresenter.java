package com.vv.androidreview.mvp.review;

import android.content.Context;

import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.repository.ContentRepository;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.mvp.system.CodeConfig;
import com.vv.androidreview.mvp.system.StaticValues;

import java.util.List;

/**
 * Created by zhiwei.a.fan on 3/3/2017.
 */

public class ReviewPresenter implements ReviewContract.ReviewPresenter {

    private ReviewDocDataSource mReviewDocDataSource;
    private ReviewContract.ReviewView mReviewView;

    public ReviewPresenter(Context context, ReviewContract.ReviewView reviewView) {
        mReviewDocDataSource = new ContentRepository(context);
        mReviewView = reviewView;
    }

    @Override
    public void requestPoint(boolean isReadCache, final boolean isAutoRequest) {
        mReviewDocDataSource.getPoints(new OnLoadDataCallBack<List<Point>>() {
            @Override
            public void onSuccess(List<Point> data) {
                if (isAutoRequest) {
                    mReviewView.completeLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_HIDE);
                } else {
                    mReviewView.showRefreshResultResponse(StaticValues.REQUEST_SUCCESS);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                if (isAutoRequest) {
                    mReviewView.completeLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_ERROR);
                } else {
                    mReviewView.showRefreshResultResponse(errorMsg);
                }
            }
        }, isReadCache);
    }
}
