package com.vv.androidreview.mvp.base;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public interface BaseRefreshableView<T> extends BaseView<T>{

    void completeLoading(int statusType);

    void showRefreshResultResponse(String msg);
}
