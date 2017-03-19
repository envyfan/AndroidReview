package com.vv.androidreview.mvp.tools;

import com.vv.androidreview.mvp.base.RefreshableView;
import com.vv.androidreview.mvp.config.CodeConfig;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class RequestDataUICallBackHelper {

    public static void normalRequestSuccess(boolean isAutoRequest, RefreshableView refreshableView) {

        if (isAutoRequest) {
            refreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_HIDE);
        } else {
            refreshableView.completePullToRefresh();
        }

    }

    public static void normalRequestFail(boolean isAutoRequest, RefreshableView refreshableView, String errorMsg) {
        if (isAutoRequest) {
            refreshableView.completeDataLoading(CodeConfig.LoadingLayoutConfig.LAYOUT_TYPE_ERROR);
        } else {
            refreshableView.completePullToRefresh(errorMsg);
        }
    }
}
