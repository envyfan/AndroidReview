package com.vv.androidreview.mvp.data.remote;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.base.system.AppContext;
import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.mvp.config.AppConfig;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.entity.Unit;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.mvp.config.CodeConfig;
import com.vv.androidreview.mvp.config.StaticValues;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Vv
 * 2016/9/1 0001.
 * Version 1.0
 * Description：
 */

public class ReviewDocRemoteDataSource implements ReviewDocDataSource {
    private Context mContext;

    public ReviewDocRemoteDataSource(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getContents(@NonNull final OnLoadDataCallBack<List<Content>> onLoadDataCallBack, final Point point, Boolean isReadCache) {
        BmobQuery<Content> query = new BmobQuery<>();
        query.setLimit(AppConfig.BaseConfig.COMMON_LIST_LIMIT);

        String sql = "select title,small,createdAt from Content where point='" + point.getObjectId() + "' order by updatedAt DESC";
        query.doSQLQuery(AppContext.getInstance(), sql, new SQLQueryListener<Content>() {
            @Override
            public void done(BmobQueryResult<Content> bmobQueryResult, BmobException e) {
                if (e == null) {
                    //请求成功
                    final List<Content> list = bmobQueryResult.getResults();
                    onLoadDataCallBack.onSuccess(list);
                    if (list != null && list.size() > 0) {
                        cacheData(list, CacheHelper.CONTENT_LIST_CACHE_KEY + point.getObjectId());
                    }
                } else {
                    onLoadDataCallBack.onFail(CodeConfig.Error.API, StaticValues.REQUEST_ERROR);
                }
            }
        });
    }

    @Override
    public void getMoreContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, int currentPage, int currentItemCount) {

    }

    /**
     * 获取单元列表
     *
     * @param loadUnitsCallback 回调
     * @param isReadCache       是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getUnits(@NonNull final OnLoadDataCallBack<List<Unit>> loadUnitsCallback, Boolean isReadCache) {
        //初始化Bmob查询类
        BmobQuery<Unit> query = new BmobQuery<>();
        //执行查询，查询单元表 取出所有单元
        query.order("score,sort");
        query.setLimit(AppConfig.ReviewPageConfig.PAGE_REVIEW_LIST_LIMIT);
        query.findObjects(mContext, new FindListener<Unit>() {
            @Override
            public void onSuccess(final List<Unit> unitList) {
                loadUnitsCallback.onSuccess(unitList);

                //有数据才保存缓存，把原来的缓存覆盖
                if (unitList != null && unitList.size() > 0) {
                    cacheData(unitList, CacheHelper.GROUP_UNIT_LIST_CACHE_KEY);
                }
            }

            @Override
            public void onError(int i, String s) {
                loadUnitsCallback.onFail(CodeConfig.Error.API, StaticValues.REQUEST_ERROR);
            }
        });
    }

    /**
     * 获取知识点列表
     *
     * @param loadPointsCallback 回调
     * @param isReadCache        是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getPoints(@NonNull final OnLoadDataCallBack<List<Point>> loadPointsCallback, Boolean isReadCache) {
        //执行查询，查询知识点表 取出所有知识点
        BmobQuery<Point> query = new BmobQuery<>();
//        query.setLimit(ReviewFragment.LIMIT);
        query.setLimit(20);
        query.findObjects(mContext, new FindListener<Point>() {
            @Override
            public void onSuccess(final List<Point> pointList) {
                loadPointsCallback.onSuccess(pointList);

                //有数据才保存缓存，把原来的缓存覆盖
                if (pointList != null && pointList.size() > 0) {
                    cacheData(pointList, CacheHelper.GROUP_POINT_LIST_CACHE_KEY);
                }
            }

            @Override
            public void onError(int i, String s) {
                loadPointsCallback.onFail(CodeConfig.Error.API, StaticValues.REQUEST_ERROR);
            }
        });
    }

    private void cacheData(final Object object, final String cacheKey) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    CacheHelper.saveObject(mContext, (Serializable) object, cacheKey);
                } catch (Exception e) {
                    Logger.e("save points cache failed");
                }
            }
        });
    }
}
