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

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.R;
import com.vv.androidreview.base.system.AppManager;
import com.vv.androidreview.base.system.StatusBarCompat;
import com.vv.androidreview.utils.TDevice;


/**
 * Author：Vv on 2015/7/21 14:41
 * Mail：envyfan@qq.com
 * Description：
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
//        隐藏ToolBar
//        hideToolBar();
        Logger.d("当前Activity 栈中有：" + AppManager.getAppManager().getActivityCount() + "个Activity");
    }


    /**
     * 隐藏ToolBar
     */
    public void hideToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * 显示ToolBar
     */
    public void showToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        if (toolbar != null) {
//            toolbar.setLogo(R.mipmap.ic_top);
            toolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            toolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(toolbar);
        }
    }



    /**
     * 是否隐藏ToolBar返回按钮
     *
     * @param show
     */
    public void showOrHideToolBarNavigation(boolean show) {
        if (show) {
            toolbar.setNavigationIcon(R.mipmap.ic_top_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        }
    }

    /**
     * 是否隐藏ToolBar Logo
     *
     * @param show
     */
    public void showOrHideToolBarLogo(boolean show) {
        if (show) {
//            toolbar.setLogo(R.mipmap.ic_top);
        } else {
            toolbar.setLogo(null);
        }
    }

    /**
     * 设置兼容4.4版本 状态栏颜色改变成和5.0效果差不多
     * 1、需要在values-19加上支持SystemWindows
     * 2、ToolBar添加属性 android:fitsSystemWindows="true"
     * 3、然后在加载完activity布局后调用该方法
     */
    public void setStatusBarCompat() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.theme_color));

    }

    public void setStatusBarCompat(int colorId) {
        StatusBarCompat.compat(this, getResources().getColor(colorId));
    }


    public abstract String returnToolBarTitle();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void setTitle() {
        if (toolbar != null) {
            String title = returnToolBarTitle();
            toolbar.setTitle(title);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
        TDevice.hideSoftKeyboard(getCurrentFocus());
//        setStatusBarCompat();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }


    /**
     * 设置布局前的操作
     */
    protected void onBeforeSetContentLayout() {
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
