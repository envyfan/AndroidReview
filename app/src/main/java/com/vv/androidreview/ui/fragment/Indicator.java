package com.vv.androidreview.ui.fragment;


import com.vv.androidreview.R;

/**
 * Author: Vv on 2015/7/24 16:08
 * Mail: envyfan@qq.com
 * Description:
 */
public enum Indicator {

    REVIEW(0, R.string.main_tab_name_review, R.drawable.tab_icon_review,
            ReviewFragment.class),

    TEST(1, R.string.main_tab_name_test, R.drawable.tab_icon_test,
            TestFragment.class),

    SETTING(2, R.string.main_tab_name_setting, R.drawable.tab_icon_other,
            SettingFragment.class);



    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private Indicator(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
