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

package com.vv.androidreview.mvp.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

/**
 * Author：Vv on 2015/7/20 20:39
 * Mail：envyfan@qq.com
 * Description：
 */
public class BaseApplication extends Application{

    public static Context sContext;
    public static Resources sResource;

    private static String PREF_NAME = "creativelocker.pref";
    private static String LAST_REFRESH_TIME = "last_refresh_time.pref";
    private static long lastToastTime;
    //运行系统是否为2.3或以上
    public static boolean isAtLeastGB;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            isAtLeastGB = true;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sResource = sContext.getResources();
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) sContext;
    }

    public static Resources resources() {
        return sResource;
    }

}
