package com.vv.androidreview.mvp.review;

import com.vv.androidreview.mvp.base.BasePresenter;
import com.vv.androidreview.mvp.base.BaseRefreshableView;

/**
 * Created by zhiwei.a.fan on 3/3/2017.
 */

public interface ReviewContract {

    interface ReviewView extends BaseRefreshableView<ReviewPresenter> {

    }

    interface ReviewPresenter extends BasePresenter {
        void requestPoint(boolean isReadCache, boolean isAutoRequest);
    }
}
