package com.vv.androidreview.mvp.config;

/**
 * Created by zhiwei.a.fan on 3/3/2017.
 */

public class CodeConfig {

    public static final class LoadingLayoutConfig {
        public static final int LAYOUT_NULL = -1;
        public static final int LAYOUT_TYPE_HIDE = 0;
        public static final int LAYOUT_TYPE_ERROR = 1;
        public static final int LAYOUT_TYPE_LOADING = 2;
        public static final int LAYOUT_TYPE_REFRESH = 3;
    }

    public static final class Error{
        public static final int API = 1;
        public static final int CACHE = 2;
    }
}
