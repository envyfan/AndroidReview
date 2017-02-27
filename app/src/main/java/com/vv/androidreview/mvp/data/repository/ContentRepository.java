package com.vv.androidreview.mvp.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
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
    private ReviewDocDataSource localDataSource;
    private ReviewDocDataSource remoteDataSource;


    public ContentRepository(Context mContext, ReviewDocDataSource localDataSource, ReviewDocDataSource remoteDataSource) {
        this.mContext = mContext;
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * 获取Contents列表(先从缓存拿，没有则从网络拿)
     *
     * @param loadContentsCallback 加载Content列表回调处理
     * @param point                对应的知识点
     * @param isNeedCache            是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, Boolean isNeedCache) {
        boolean isHasCache = false;

        if(point != null) {
            isHasCache = CacheHelper.isExistDataCache(mContext, CacheHelper.CONTENT_LIST_CACHE_KEY + point.getObjectId());
        }

        if(isHasCache && isNeedCache){
            Logger.d("has ContentList Cache");
            localDataSource.getContents(loadContentsCallback,point,null);
            return ;
        }

        //本地没数据则向网络请求数据
        if(isNeedCache){
            Logger.d("no ContentList Cache");
        }

        remoteDataSource.getContents(loadContentsCallback,point,null);
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
        remoteDataSource.getMoreContents(loadContentsCallback,point,currentPage,currentItemCount);
    }
}
