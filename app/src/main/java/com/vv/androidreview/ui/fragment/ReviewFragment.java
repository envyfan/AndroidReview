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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.vv.androidreview.R;
import com.vv.androidreview.adapter.ReviewListAdapter;
import com.vv.androidreview.adapter.ReviewListAdapterGV;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.entity.Unit;
import com.vv.androidreview.cache.ReadCacheAsyncTask;
import com.vv.androidreview.cache.SaveCacheAsyncTask;
import com.vv.androidreview.ui.view.LoadingLayout;
import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.utils.ToastHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class ReviewFragment extends BasePutToRefreshFragment<ReviewListAdapterGV> {

    public static final String ARGUMENT_POINT_KEY = "argument_point_key";

    private ReviewListAdapterGV mReviewListAdapter;

    @Override
    public View getRootView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_review, null, false);
    }

    @Override
    public ReviewListAdapterGV getAdapter() {
        mReviewListAdapter = new ReviewListAdapterGV(getContext());
        return mReviewListAdapter;
    }

    public static ReviewFragment newInstance() {

        Bundle args = new Bundle();

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void readCache() {
        readCache(CacheHelper.GROUP_LIST_CACHE_KEY);
    }

    @Override
    public void requestDataByNet(int actionType) {
        putToRefreshByUnit();
    }

    @Override
    public void initArguments() {
    }

    @Override
    public void createViewsOrListener() {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * “单元”列表下拉刷新具体实现
     */
    private void putToRefreshByUnit() {
        //初始化Bmob查询类
        BmobQuery<Unit> query = new BmobQuery<>();
        //执行查询，查询单元表 取出所有单元
        query.findObjects(getContext(), new FindListener<Unit>() {
            @Override
            public void onSuccess(final List<Unit> unitList) {
                //根据查询的所有单元，请求所有的知识点数据
                requestPointByUnits(unitList);
            }
            @Override
            public void onError(int i, String s) {
                toastError(mLoadingLayout, getContext());
            }
        });

    }

    private void requestPointByUnits(final List<Unit> unitList) {
        //初始化Adapter数据结构
        final List<Map<String, List<Point>>> listGruop = new ArrayList<>();
        //执行查询，查询知识点表 取出所有知识点
        BmobQuery<Point> query = new BmobQuery<>();
        query.findObjects(getContext(), new FindListener<Point>() {
            @Override
            public void onSuccess(List<Point> pointList) {
                //打包Adapter数据
                packetAdapterData(pointList, unitList, listGruop);
                //更新Adapter
                mAdapter.notifyAdapter(listGruop);
                if (listGruop.size() != 0) {
                    //把数据缓存到本地
                    SaveCacheAsyncTask savecaheTask = new SaveCacheAsyncTask(getContext(), (Serializable) listGruop, CacheHelper.GROUP_LIST_CACHE_KEY);
                    savecaheTask.execute();
                }
                //更新UI
                mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
                mPtrFrameLayout.setVisibility(View.VISIBLE);
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                toastError(mLoadingLayout, getContext());
            }
        });
    }

    /**
     *
     * @param pointList 包含所有知识点
     * @param unitList 包含所有单元
     * @param listGruop 承载组织好 以String, List<Point> 形式的数据结构 String是单元名称，List<Point>是该单元对应的知识点。
     */
    private void packetAdapterData(List<Point> pointList, List<Unit> unitList, List<Map<String, List<Point>>> listGruop) {
        //根据所有单元，组合好以Map<String,List<Point>> 形式的View以便adapter好处理
        for (Unit unit : unitList) {
            Map<String, List<Point>> map = new HashMap<>();
            List<Point> pointsForUnit = new ArrayList<Point>();
            for (Point point : pointList) {
                if (point.getUnit().getObjectId().equals(unit.getObjectId())) {
                    pointsForUnit.add(point);
                }
            }

            //如果无数据，则插入一个空数据用于 友好提示
            if (pointsForUnit.size() == 0) {
                Point point = new Point();
                point.setName("暂无内容\n敬请期待");
                point.setColor(ReviewListAdapter.NO_CONTENT);
                pointsForUnit.add(point);
            }
            map.put(unit.getName(), pointsForUnit);
            listGruop.add(map);
        }
    }

    private void toastError(LoadingLayout loadingLayout, Context context) {
        if (loadingLayout.getState() == LoadingLayout.STATE_REFRESH) {
            loadingLayout.setLoadingLayout(LoadingLayout.NETWORK_ERROR);
        }
        if(mPtrFrameLayout!=null&&mPtrFrameLayout.isRefreshing()){
            mPtrFrameLayout.refreshComplete();
        }
        ToastHelper.showString(context, context.getString(R.string.fail_put_to_refresh));
    }

    /**
     * 读取缓存
     *
     * @param cacheKey
     */
    private void readCache(String cacheKey) {
        ReadCacheAsyncTask<List<Map<String, List<Point>>>> readCache = new ReadCacheAsyncTask<>(getContext());
        readCache.setOnReadCacheToDo(new ReadCacheAsyncTask.OnReadCacheToDo<List<Map<String, List<Point>>>>() {
            @Override
            public void preExecute() {
                mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
                mPtrFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void postExecute(List<Map<String, List<Point>>> data) {
                if (data == null || data.size() == 0) {
                    mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_REFRESH);
                    mPtrFrameLayout.autoRefresh(true);
                } else {
                    mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
                    mPtrFrameLayout.setVisibility(View.VISIBLE);
                    mReviewListAdapter.setDatas(data);
                }

            }
        });

        readCache.execute(cacheKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPtrFrameLayout!=null&&mPtrFrameLayout.isRefreshing()){
            mPtrFrameLayout.refreshComplete();
        }
    }
}
