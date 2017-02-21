package com.vv.androidreview.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public abstract class BaseLazyFragment extends BaseFragment {

    private boolean isViewCreated = false;
    private boolean isSpecialLazyLoad = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            lazyLoadData();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        if (isSpecialLazyLoad && getUserVisibleHint()) {
            lazyLoadData();
            isSpecialLazyLoad = false;
        }
    }

    private void lazyLoadData() {
        if (!isViewCreated) {
            isSpecialLazyLoad = true;
            return;
        }
        onLazyDataLoad();
    }

    public abstract void onLazyDataLoad();

}
