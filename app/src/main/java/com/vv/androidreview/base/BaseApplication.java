package com.vv.androidreview.base;

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
