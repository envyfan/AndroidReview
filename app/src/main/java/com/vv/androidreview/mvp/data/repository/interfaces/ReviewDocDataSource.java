package com.vv.androidreview.mvp.data.repository.interfaces;

import android.support.annotation.NonNull;

import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;

import java.util.List;

/**
 * Created by zhiwei.a.fan on 2/27/2017.
 */

public interface ReviewDocDataSource {

    /**
     * 获取Contents列表(先从缓存拿，没有则从网络拿)
     * @param loadContentsCallback 加载Content列表回调处理
     * @param point 对应的知识点
     * @param isNeedCache 是否需要缓存 (具体 local 和 remote可以传null)
     */
    void getContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, Boolean isNeedCache);

    /**
     * 获取Contents列表(从网上拿，不拿缓存，加载更多)
     *
     * @param loadContentsCallback 回调处理
     * @param point                对应的知识点
     * @param currentItemCount                当前列表Item总数
     * @param currentPage                当前页
     */
    void getMoreContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, int currentPage, int currentItemCount);

}
