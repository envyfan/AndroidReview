package com.vv.androidreview.mvp.review;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.mvp.base.RecycleRefreshableView;
import com.vv.androidreview.mvp.base.RefreshableView;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.entity.Unit;
import com.vv.androidreview.mvp.data.repository.ContentRepository;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.mvp.config.CodeConfig;
import com.vv.androidreview.mvp.system.StaticValues;
import com.vv.androidreview.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhiwei.a.fan on 3/3/2017.
 */

public class ReviewPresenter implements ReviewContract.ReviewPresenter {

    private ReviewDocDataSource mReviewDocDataSource;
    private ReviewContract.ReviewView mReviewView;
    private RefreshableView mRefreshableView;
    private RecycleRefreshableView<ReviewListAdapter> mRecycleRefreshableView;

    private List<Map<String, List<Point>>> mData = new ArrayList<>();

    public ReviewPresenter(Context context, ReviewContract.ReviewView reviewView,
                           RefreshableView refreshableView,
                           RecycleRefreshableView<ReviewListAdapter> recycleRefreshableView) {
        mReviewDocDataSource = new ContentRepository(context);
        mReviewView = reviewView;
        this.mRefreshableView = refreshableView;
        this.mRecycleRefreshableView = recycleRefreshableView;
    }

    @Override
    public void requestData(final boolean isReadCache, final boolean isAutoRequest) {

        //先拿有多少个单元
        mReviewDocDataSource.getUnits(new OnLoadDataCallBack<List<Unit>>() {
            @Override
            public void onSuccess(List<Unit> data) {
                //成功拿完单元后 再拿单元下的知识点
                if (data != null && data.size() > 0) {
                    Logger.d("unit size : " + data.size());
                    getPoints(data, isAutoRequest, isReadCache);
                } else {
                    mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_REFRESH);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                if (isAutoRequest) {
                    mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_ERROR);
                } else {
                    mRefreshableView.completePullToRefresh(errorMsg);
                }
            }
        }, isReadCache);
    }

    private void getPoints(final List<Unit> units, final boolean isAutoRequest, boolean isReadCache) {
        mReviewDocDataSource.getPoints(new OnLoadDataCallBack<List<Point>>() {
            @Override
            public void onSuccess(List<Point> data) {
                if (data != null && data.size() > 0) {
                    Logger.d("point size : " + data.size());
                    //清空数据
                    clearData();
                    //根据所有单元，组合好以Map<String,List<Point>> 形式的View以便adapter好处理
                    packageAdapterData(data, units);
                    updateDataForAdapter();

                    if (isAutoRequest) {
                        mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_HIDE);
                    } else {
                        mRefreshableView.completePullToRefresh();
                    }

                } else {
                    mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_REFRESH);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                if (isAutoRequest) {
                    mRefreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_ERROR);
                } else {
                    mRefreshableView.completePullToRefresh(errorMsg);
                }
            }
        }, isReadCache);
    }

    private void packageAdapterData(List<Point> points, List<Unit> units) {
        //根据所有单元，组合好以Map<String,List<Point>> 形式的View以便adapter好处理
        for (Unit unit : units) {
            Map<String, List<Point>> map = new HashMap<>();
            List<Point> pointsForUnit = new ArrayList<Point>();
            for (Point point : points) {
                //bmob的object一定有objectId 这里懒得判空了 一定不为空
                if (StringUtils.replaceBlank(point.getUnit().getObjectId()).equals(StringUtils.replaceBlank(unit.getObjectId()))) {
                    pointsForUnit.add(point);
                }
            }

            //如果无数据，则插入一个空数据用于 友好提示
            if (pointsForUnit.size() == 0) {
                Point point = new Point();
                point.setName(StaticValues.DEFAULT_POINT_NAME);
                point.setColor(ReviewListAdapter.NO_CONTENT);
                pointsForUnit.add(point);
            }
            map.put(unit.getName(), pointsForUnit);
            mData.add(map);
        }
    }

    private void clearData() {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
    }

    private void updateDataForAdapter() {
        ReviewListAdapter adapter = mRecycleRefreshableView.getAdapter();
        adapter.setData(mData);
        adapter.notifyDataSetChanged();
    }
}
