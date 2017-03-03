package com.vv.androidreview.mvp.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.entity.Unit;
import com.vv.androidreview.mvp.data.local.ReviewDocLocalDataSource;
import com.vv.androidreview.mvp.data.remote.ReviewDocRemoteDataSource;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;

import java.util.List;

/**
 * Created by Vv
 * 2016/9/1 0001.
 * Version 1.0
 * Description：
 */
public class ContentRepository implements ReviewDocDataSource {

    private Context mContext;
    private ReviewDocDataSource mLocalDataSource;
    private ReviewDocDataSource mRemoteDataSource;


    public ContentRepository(Context mContext) {
        this.mContext = mContext;
        this.mLocalDataSource = new ReviewDocLocalDataSource(mContext);
        this.mRemoteDataSource = new ReviewDocRemoteDataSource(mContext);
    }

    /**
     * 获取Contents列表(先从缓存拿，没有则从网络拿)
     *
     * @param loadContentsCallback 加载Content列表回调处理
     * @param point                对应的知识点
     * @param isReadCache          是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, Boolean isReadCache) {
        boolean isHasCache = false;

        if (point != null) {
            isHasCache = CacheHelper.isExistDataCache(mContext, CacheHelper.CONTENT_LIST_CACHE_KEY + point.getObjectId());
        }

        if (isReadCache && isHasCache) {
            Logger.d("has ContentList Cache");
            mLocalDataSource.getContents(loadContentsCallback, point, null);
            return;
        }

        //本地没数据则向网络请求数据
        if (isReadCache) {
            Logger.d("no ContentList Cache");
        }

        mRemoteDataSource.getContents(loadContentsCallback, point, null);
    }

    /**
     * 获取Contents列表(从网上拿，不拿缓存，加载更多)
     *
     * @param loadContentsCallback 回调处理
     * @param point                对应的知识点
     * @param currentPage          当前页
     * @param currentItemCount     当前列表Item总数
     */
    @Override
    public void getMoreContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, int currentPage, int currentItemCount) {
        mRemoteDataSource.getMoreContents(loadContentsCallback, point, currentPage, currentItemCount);
    }

    /**
     * 获取单元列表
     *
     * @param loadUnitsCallback 回调
     * @param isReadCache       是否需要缓存 false 请求远程服务器 true 具体的local和remote实现 忽略此参数
     */
    @Override
    public void getUnits(@NonNull OnLoadDataCallBack<List<Unit>> loadUnitsCallback, Boolean isReadCache) {
        boolean hasCache = CacheHelper.isExistDataCache(mContext, CacheHelper.GROUP_UNIT_LIST_CACHE_KEY);
        //假如有缓存则读缓存
        if (hasCache && isReadCache) {
            Logger.d("has Units Cache");
            mLocalDataSource.getUnits(loadUnitsCallback, null);
            return;
        }

        //本地没数据则向网络请求数据
        if (isReadCache) {
            Logger.d("no Units Cache");
        }
        //从网络获取数据
        mRemoteDataSource.getUnits(loadUnitsCallback, null);
    }

    /**
     * 获取知识点列表
     *
     * @param loadPointsCallback 回调
     * @param isReadCache        是否需要缓存 false 请求远程服务器 true 具体的local和remote实现 忽略此参数
     */
    @Override
    public void getPoints(@NonNull OnLoadDataCallBack<List<Point>> loadPointsCallback, Boolean isReadCache) {
        boolean hasCache = CacheHelper.isExistDataCache(mContext, CacheHelper.GROUP_POINT_LIST_CACHE_KEY);
        //假如有缓存则读缓存
        if (hasCache && isReadCache) {
            Logger.d("has points Cache");
            mLocalDataSource.getPoints(loadPointsCallback, null);
            return;
        }

        //本地没数据则向网络请求数据
        if (isReadCache) {
            Logger.d("no points Cache");
        }
        //从网络获取数据
        mRemoteDataSource.getPoints(loadPointsCallback, null);
    }
}
