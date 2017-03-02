package com.vv.androidreview.mvp.data.remote;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.cache.CacheHelper;
import com.vv.androidreview.mvp.data.entity.Content;
import com.vv.androidreview.mvp.data.entity.Point;
import com.vv.androidreview.mvp.data.entity.Unit;
import com.vv.androidreview.mvp.data.repository.interfaces.OnLoadDataCallBack;
import com.vv.androidreview.mvp.data.repository.interfaces.ReviewDocDataSource;
import com.vv.androidreview.ui.fragment.ReviewFragment;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Vv
 * 2016/9/1 0001.
 * Version 1.0
 * Description：
 */

public class ReviewDocRemoteDataSource implements ReviewDocDataSource{
    private Context mContext;

    public ReviewDocRemoteDataSource(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, Boolean isNeedCache) {

    }

    @Override
    public void getMoreContents(@NonNull OnLoadDataCallBack<List<Content>> loadContentsCallback, Point point, int currentPage, int currentItemCount) {

    }

    /**
     * 获取单元列表
     *
     * @param loadUnitsCallback 回调
     * @param isNeedCache 是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getUnits(@NonNull final OnLoadDataCallBack<List<Unit>> loadUnitsCallback, Boolean isNeedCache) {
        //初始化Bmob查询类
        BmobQuery<Unit> query = new BmobQuery<>();
        //执行查询，查询单元表 取出所有单元
        query.order("score,sort");
//        query.setLimit(ReviewFragment.LIMIT);
        query.setLimit(2);
        query.findObjects(mContext, new FindListener<Unit>() {
            @Override
            public void onSuccess(final List<Unit> unitList) {
                loadUnitsCallback.onSuccess(unitList);

                //有数据才保存缓存，把原来的缓存覆盖
                if(unitList != null && unitList.size() > 0){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                CacheHelper.saveObject(mContext,(Serializable)unitList,CacheHelper.GROUP_UNIT_LIST_CACHE_KEY);
                            }catch(Exception e){
                                Logger.e("保存单元缓存失败");
                            }
                        }
                    });
                }
            }
            @Override
            public void onError(int i, String s) {
                loadUnitsCallback.onFail(1,"");
            }
        });
    }

    /**
     * 获取知识点列表
     *
     * @param loadPointsCallback 回调
     * @param isNeedCache 是否需要缓存 (具体 local 和 remote可以传null)
     */
    @Override
    public void getPoints(@NonNull final OnLoadDataCallBack<List<Point>> loadPointsCallback, Boolean isNeedCache) {
        //执行查询，查询知识点表 取出所有知识点
        BmobQuery<Point> query = new BmobQuery<>();
//        query.setLimit(ReviewFragment.LIMIT);
        query.setLimit(20);
        query.findObjects(mContext, new FindListener<Point>() {
            @Override
            public void onSuccess(final List<Point> pointList) {
                loadPointsCallback.onSuccess(pointList);

                //有数据才保存缓存，把原来的缓存覆盖
                if(pointList != null && pointList.size() > 0) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CacheHelper.saveObject(mContext, (Serializable) pointList, CacheHelper.GROUP_POINT_LIST_CACHE_KEY);
                            } catch (Exception e) {
                                Logger.e("保存知识点缓存失败");
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
                loadPointsCallback.onFail(1,"");
            }
        });
    }
}
