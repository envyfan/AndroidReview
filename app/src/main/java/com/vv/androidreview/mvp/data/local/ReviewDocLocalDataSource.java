package com.vv.androidreview.mvp.data.local;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.entity.Unit;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.mvp.config.CodeConfig;
import com.vv.androidreview.mvp.config.StaticValues;

import java.util.List;
import java.util.Objects;

/**
 * Created by Vv
 * 2016/9/1 0001.
 * Version 1.0
 * Description：
 */

public class ReviewDocLocalDataSource implements ReviewDocDataSource {

    private Context mContext;

    public ReviewDocLocalDataSource(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getContents(@NonNull final OnLoadDataCallBack<List<Content>> loadDataCallBack, final Point point, Boolean isReadCache) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Content> contentList = (List<Content>) CacheHelper.readObject(mContext, CacheHelper.CONTENT_LIST_CACHE_KEY + point.getObjectId());
                    loadDataCallBack.onSuccess(contentList);
                } catch (Exception e) {
                    loadDataCallBack.onFail(CodeConfig.Error.CACHE, StaticValues.REQUEST_CACHE_ERROR);
                }

            }
        });
    }

    @Override
    public void getMoreContents(@NonNull OnLoadDataCallBack<List<Content>> loadDataCallBack, Point point, int currentPage, int currentItemCount) {

    }

    /**
     * 获取单元列表
     *
     * @param loadDataCallBack 回调
     * @param isReadCache       是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getUnits(@NonNull final OnLoadDataCallBack<List<Unit>> loadDataCallBack, Boolean isReadCache) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Unit> units = (List<Unit>) CacheHelper.readObject(mContext, CacheHelper.GROUP_UNIT_LIST_CACHE_KEY);
                    loadDataCallBack.onSuccess(units);
                } catch (Exception e) {
                    loadDataCallBack.onFail(CodeConfig.Error.CACHE, StaticValues.REQUEST_CACHE_ERROR);
                }

            }
        });
    }

    /**
     * 获取知识点列表
     *
     * @param loadDataCallBack 回调
     * @param isReadCache        是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getPoints(@NonNull final OnLoadDataCallBack<List<Point>> loadDataCallBack, Boolean isReadCache) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Point> points = (List<Point>) CacheHelper.readObject(mContext, CacheHelper.GROUP_POINT_LIST_CACHE_KEY);
                    loadDataCallBack.onSuccess(points);
                } catch (Exception e) {
                    loadDataCallBack.onFail(CodeConfig.Error.CACHE, StaticValues.REQUEST_CACHE_ERROR);
                }

            }
        });
    }
}
