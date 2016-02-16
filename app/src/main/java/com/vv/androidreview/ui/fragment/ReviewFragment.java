package com.vv.androidreview.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.vv.androidreview.R;
import com.vv.androidreview.adapter.ReviewListAdapter;
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
public class ReviewFragment extends BasePutToRefreshFragment<ReviewListAdapter> {

    public static final String ARGUMENT_POINT_KEY = "argument_point_key";

    private ReviewListAdapter mReviewListAdapter;

    @Override
    public View getRootView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_review, null, false);
    }

    @Override
    public ReviewListAdapter getAdapter() {
        mReviewListAdapter = new ReviewListAdapter(getContext());
        return mReviewListAdapter;
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
                //初始化Adapter数据
                final List<Map<String, List<Point>>> listGruop = new ArrayList<>();
                //执行查询，查询知识点表 取出所有知识点
                BmobQuery<Point> query = new BmobQuery<>();
                query.findObjects(getContext(), new FindListener<Point>() {
                    @Override
                    public void onSuccess(List<Point> pointList) {
                        //根据所有单元，组合好以<String,List<Point>> 形式的View以便adapter好处理
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
                        //adapter绑定数据后并且结束下来刷新的加载
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

            @Override
            public void onError(int i, String s) {
                toastError(mLoadingLayout, getContext());
            }
        });

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
            public void postExectue(List<Map<String, List<Point>>> data) {
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
