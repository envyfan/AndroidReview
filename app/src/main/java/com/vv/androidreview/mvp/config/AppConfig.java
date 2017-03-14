package com.vv.androidreview.mvp.config;

import android.Manifest;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class AppConfig {

    /**
     * 应用基础配置
     */
    public static final class BaseConfig {

        /**
         * 应用启动引导界面延迟时间
         */
        public static final int APP_START_DELAY_TIME = 2000;

        /**
         * 首页列表长度
         **/
        public static final int HOME_SCREEN_LIST_LIMIT = 10;
        /**
         * 公共默认列表长度
         **/
        public static final int COMMON_LIST_LIMIT = 15;

        /**
         * 应用所需的全部敏感权限
         */
        public static final String[] SENSITIVE_PERMISSIONS = new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }
}
