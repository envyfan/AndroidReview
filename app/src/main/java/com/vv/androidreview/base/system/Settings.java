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

package com.vv.androidreview.base.system;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：存取Settings SharePrefrence 配置
 */
public class Settings {

    public final static String FILE_CONFIG = "file_config";

    /**
     * wifi缓存过期时间 分钟为单位
     */
    public final static String CACHE_OVERTIME_WIFI = "cache_overtime_wifi";
    /**
     * 其他网络缓存过期时间 天为单位
     */
    public final static String CACHE_OVERTIME_OTHER = "cache_overtime_other";
    /**
     * 是否开启缓存不过期
     */
    public final static String CACHE_OVERTIME = "cache_overtime";

    public static SharedPreferences getPreferences(String prefName) {
        return AppContext.getInstance().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    private static void apply(SharedPreferences.Editor editor) {
        if (AppContext.isAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void putInt(String key, int value) {
        SharedPreferences preferences = getPreferences(FILE_CONFIG);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static int getInt(String key) {
        return getPreferences(FILE_CONFIG).getInt(key, -1);
    }

    public static int getInt(String key,int defaultInt) {
        return getPreferences(FILE_CONFIG).getInt(key, defaultInt);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences preferences = getPreferences(FILE_CONFIG);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static Boolean getBoolean(String key) {
        return getPreferences(FILE_CONFIG).getBoolean(key, false);
    }

    public static Boolean getBoolean(String key,boolean defaultBoolean) {
        return getPreferences(FILE_CONFIG).getBoolean(key, defaultBoolean);
    }
}
