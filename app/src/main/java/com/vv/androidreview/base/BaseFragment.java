/*
 * Copyright (c) 2016. Vv <envyfan@qq.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vv.androidreview.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vv.androidreview.mvp.base.BaseActivity;


/**
 * Author: Vv on 2015/8/12 17:26
 * Mail: envyfan@qq.com
 * Description:
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    protected boolean isCreate = true;

    protected BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (BaseActivity) activity;
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCreate = false;
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 当Fragment可见时调用该方法
     */
    protected void onVisible() {

    }

    /**
     * Fragment不可见时调用
     */
    protected void onInvisible() {
    }


}
