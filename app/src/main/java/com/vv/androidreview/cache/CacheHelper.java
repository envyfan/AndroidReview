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

package com.vv.androidreview.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.vv.androidreview.base.system.AppContext;
import com.vv.androidreview.base.system.Settings;
import com.vv.androidreview.utils.TDevice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class CacheHelper {
    // wifi缓存时间为10分钟
    private static long wifi_cache_time = 10 * 60 * 1000;
    // 其他网络环境为48小时
    private static long other_cache_time = 2 * 24 * 60 * 60 * 1000;

    public final static String FAV = "fav.pref";
    public final static String GROUP_LIST_CACHE_KEY = "grup_list";
    public final static String CONTENT_LIST_CACHE_KEY = "content_list_";
    public final static String GROUP_UNIT_LIST_CACHE_KEY = "grup_unit_list";
    public final static String GROUP_POINT_LIST_CACHE_KEY = "grup_point_list";
    public final static String CONTENT_CACHE_KEY = "content_";
    public final static String TEST = "test_";


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

    public static long getFav(String key) {
        return getPreferences(FAV).getInt(key, -1);
    }

    public static void putToFav(String key, int value) {
        SharedPreferences preferences = getPreferences(FAV);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void removeToFav(String key) {
        SharedPreferences preferences = getPreferences(FAV);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        apply(editor);
    }


    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public static boolean saveObject(Context context, Serializable ser,
                                     String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Serializable readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null)
            return false;
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 判断缓存是否已经失效
     */
    public static boolean isCacheDataFailure(Context context, String cachefile) {
        File data = context.getFileStreamPath(cachefile);
        if (!data.exists()) {

            return false;
        }
        long existTime = System.currentTimeMillis() - data.lastModified();
        boolean failure = false;
        if (TDevice.getNetworkType() == TDevice.NETTYPE_WIFI) {
            failure = existTime > Settings.getInt(Settings.CACHE_OVERTIME_WIFI,30) * 60 * 1000 ? true : false;
        } else {
            failure = existTime > Settings.getInt(Settings.CACHE_OVERTIME_OTHER,2) * 24 * 60 * 60 * 1000 ? true : false;
        }
        return failure;
    }

    public static boolean isOpenCacheOverTime() {
        return Settings.getBoolean(Settings.CACHE_OVERTIME,false);
    }

}
