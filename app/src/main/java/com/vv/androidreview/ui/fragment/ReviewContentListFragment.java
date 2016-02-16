package com.vv.androidreview.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.adapter.ContentListAdapter;
import com.vv.androidreview.entity.Content;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.cache.ReadCacheAsyncTask;
import com.vv.androidreview.cache.SaveCacheAsyncTask;
import com.vv.androidreview.ui.activites.DetailActivity;
import com.vv.androidreview.ui.view.LoadingLayout;
import com.vv.androidreview.cache.CacheHelper;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class ReviewContentListFragment extends BasePutToRefreshFragment<ContentListAdapter> {
    public static final String ARGUMENT_CONTEN_KEY = "argument_conten_key";
    private int mContentCount = 0;
    private Point mPoint;
    private ContentListAdapter mContentListAdapter;


    @Override
    public View getRootView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_review_content_list, null, false);
        setLoadMore(true);
        return view;
    }

    @Override
    public ContentListAdapter getAdapter() {
        mContentListAdapter = new ContentListAdapter(getContext());
        return mContentListAdapter;
    }

    @Override
    public void readCache() {
        ReadCacheAsyncTask<List<Content>> readCache = new ReadCacheAsyncTask<>(getContext());
        readCache.setOnReadCacheToDo(new ReadCacheAsyncTask.OnReadCacheToDo<List<Content>>() {
            @Override
            public void preExecute() {
                mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
                mPtrFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void postExectue(List<Content> data) {
                if (data == null || data.size() == 0) {
                    mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_REFRESH);
                    mPtrFrameLayout.autoRefresh(true);
                } else {
                    mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
                    mPtrFrameLayout.setVisibility(View.VISIBLE);
                    mContentListAdapter.setDatas(data);
                }

            }
        });

        readCache.execute(CacheHelper.CONTENT_LIST_CACHE_KEY + mPoint.getName());
    }

    @Override
    public void requestDataByNet(final int actionType) {
        if (mContentCount == 0) {
            BmobQuery<Content> query = new BmobQuery<>();
            query.addWhereEqualTo("point", mPoint);
            query.count(getContext(), Content.class, new CountListener() {
                @Override
                public void onSuccess(int i) {
                    mContentCount = i;
                    putToRefreshByContent(actionType);
                }

                @Override
                public void onFailure(int i, String s) {
                    if (actionType == REFRESH_TYPE_PULL) {
                        //更新UI
                        toastError(mLoadingLayout, getContext());
                    } else {
                        sPutUpState = PULL_UP_STATE_EEROR;
                        updateFooterViewStateText();
                    }
                }
            });
        } else {
            putToRefreshByContent(actionType);
        }
    }

    @Override
    public void initArguments() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mPoint = (Point) bundle.getSerializable(ReviewFragment.ARGUMENT_POINT_KEY);
        }
    }

    @Override
    public void createViewsOrListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //它是footView时
                if (id < 0 && !(position < mAdapter.getCount())) {
                    if (sPutUpState == PULL_UP_STATE_EEROR) {
                        pullUpLoadData();
                    }
                } else {
                    Content content = (Content) parent.getItemAtPosition(position);
                    content.setPoint(mPoint);
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(ARGUMENT_CONTEN_KEY, content);
                    getContext().startActivity(intent);
                }
            }
        });
    }

    private void putToRefreshByContent(final int actionType) {
        //初始化Bmob查询类
        BmobQuery<Content> query = new BmobQuery<>();
        query.setLimit(PAGE_LIMIT);
        if (actionType == REFRESH_TYPE_PULL) {
            mCurrentPageCount = 0;
            query.setSkip(0);
        } else {
            int currentPageLimit = mAdapter.getCount();
            if (currentPageLimit < PAGE_LIMIT) {
                query.setSkip(currentPageLimit);
            } else {
                query.setSkip(PAGE_LIMIT * (mCurrentPageCount + 1));
            }
        }
        String sql = "select title,small,createdAt from Content where point='"+mPoint.getObjectId()+"' order by updatedAt DESC";
        query.doSQLQuery(getContext(), sql, new SQLQueryListener<Content>() {
            @Override
            public void done(BmobQueryResult<Content> bmobQueryResult, BmobException e) {
                if (e == null) {
                    //请求成功
                    List<Content> list = bmobQueryResult.getResults();
                    if (actionType == REFRESH_TYPE_PULL) {
                        mContentListAdapter.notifyAdapter(list);
                        //把数据缓存到本地
                        SaveCacheAsyncTask savecaheTask = new SaveCacheAsyncTask(getContext(), (Serializable) list, CacheHelper.CONTENT_LIST_CACHE_KEY + mPoint.getName());
                        savecaheTask.execute();
                        if (list.size() != 0) {
                            mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
                            mPtrFrameLayout.setVisibility(View.VISIBLE);
                        } else {
                            mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_REFRESH);
                            mPtrFrameLayout.setVisibility(View.GONE);
                        }
                        //更新UI
                        mPtrFrameLayout.refreshComplete();
                    } else {
                        //如果目前的数据数量大于等 该知识点的所有内容数量 即视为加载完全部数据。 加上后面的大于条件是为了防止 用户在上啦加载时服务器增加了一条数据而导致上啦加载出现数据错乱
                        if (mContentListAdapter.getCount() == mContentCount || mContentListAdapter.getCount() > mContentCount) {
                            sPutUpState = PULL_UP_STATE_NOMORE;
                            updateFooterViewStateText();
                        } else {
                            mContentListAdapter.addData(list);
                            removeFootView();
                        }
                    }
                } else {
                    //请求失败
                    Logger.e(e.getMessage());
                    if (actionType == REFRESH_TYPE_PULL) {
                        //更新UI
                        toastError(mLoadingLayout, getContext());
                    } else {
                        sPutUpState = PULL_UP_STATE_EEROR;
                        updateFooterViewStateText();
                    }
                }
            }
        });
    }

    private void toastError(LoadingLayout loadingLayout, Context context) {
        if (loadingLayout.getState() == LoadingLayout.STATE_REFRESH) {
            loadingLayout.setLoadingLayout(LoadingLayout.NETWORK_ERROR);
        }
        if (mPtrFrameLayout != null && mPtrFrameLayout.isRefreshing()) {
            mPtrFrameLayout.refreshComplete();
        }

//        ToastHelper.showString(context,context.getString(R.string.fail_put_to_refresh));
    }
}