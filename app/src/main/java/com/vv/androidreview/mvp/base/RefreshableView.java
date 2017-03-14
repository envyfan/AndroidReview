package com.vv.androidreview.mvp.base;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public interface RefreshableView {

    /**
     * 完成数据加载（可能从本地加载也可能从网络加载 ）
     * @param loadingLayoutStatusType  loadingLayout 状态
     */
    void completeDataLoading(int loadingLayoutStatusType);

    /**
     * 完成下拉刷新
     * @param msg complete message
     */
    void completePullToRefresh(String msg);

    /**
     * 完成下拉刷新 默认提示: {@link com.vv.androidreview.mvp.system.StaticValues.REQUEST_SUCCESS}
     */
    void completePullToRefresh();
}
