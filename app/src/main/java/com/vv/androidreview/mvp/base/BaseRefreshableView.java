package com.vv.androidreview.mvp.base;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public interface BaseRefreshableView<T> extends BaseView<T>{

    /**
     * 完成数据加载（可能从本地加载也可能从网络加载 ）
     * @param loadingLayoutStatusType  loadingLayout 状态
     */
    void completeDataLoading(int loadingLayoutStatusType);

    /**
     * 完成下来刷新
     * @param msg
     */
    void completePullToRefresh(String msg);
}
