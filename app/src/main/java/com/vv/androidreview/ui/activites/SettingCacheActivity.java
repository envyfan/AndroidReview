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

package com.vv.androidreview.ui.activites;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sevenheaven.iosswitch.ShSwitchView;
import com.vv.androidreview.R;
import com.vv.androidreview.base.system.Settings;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;
import com.vv.androidreview.ui.view.RangeSliderViewEx;

public class SettingCacheActivity extends BaseToolbarActivity {

    public static final String MINUTE = "分钟";
    public static final String DAY = "天";
    public static final String LABEL = "(自定义)";
    public static final int MINUTE_STEP = 30;
    public static final int DAY_STEP = 2;
    //过期控制开关
    private ShSwitchView mCacheSwitch;
    private RelativeLayout mWifiSwitchLayout, mOtherSwitchLayout;
    //显示选择的分钟文本
    private TextView mLabelWifi, mLabelOther;
    //输入分钟
    private EditText mEtWifi, mEtOther;

    private RangeSliderViewEx mRsvWifi, mRsvOther;
    //用于保存分钟/天数，以便退出时候保存
    private int mWifiMin;
    private int mOtherDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_cache);
        initView();
        initToolBar(true,getString(R.string.label_cache));
        initListener();
        bindData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suggest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                if (mEtWifi.getVisibility() == View.VISIBLE) {
                    String min = mEtWifi.getText().toString();
                    //严重时间是否为空
                    if (TextUtils.isEmpty(min)) {
                        Toast.makeText(SettingCacheActivity.this, getString(R.string.no_cache_over_time), Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        mWifiMin = Integer.parseInt(min);
                    }
                }
                if (mEtOther.getVisibility() == View.VISIBLE) {
                    String day = mEtOther.getText().toString();
                    //严重时间是否为空
                    if (TextUtils.isEmpty(day)) {
                        Toast.makeText(SettingCacheActivity.this, getString(R.string.no_cache_over_time), Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        mOtherDay = Integer.parseInt(day);
                    }

                }
                Settings.putInt(Settings.CACHE_OVERTIME_WIFI, mWifiMin);
                Settings.putInt(Settings.CACHE_OVERTIME_OTHER, mOtherDay);
                Settings.putBoolean(Settings.CACHE_OVERTIME, mCacheSwitch.isOn());
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListener() {
        mCacheSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                showOrHideLayout(isOn);
            }
        });

        mRsvWifi.setOnSlideListener(new RangeSliderViewEx.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                //最后一段自定义时间
                int lastIndex = (mRsvWifi.getRangeCount() - 1);
                if (index == lastIndex) {
                    mEtWifi.setText(mWifiMin + "");
                    mEtWifi.setVisibility(View.VISIBLE);
                    mEtWifi.requestFocus();
                    mLabelWifi.setText(MINUTE+ LABEL);
                } else {
                    mEtWifi.setVisibility(View.GONE);
                    mWifiMin = (index + 1) * MINUTE_STEP;
                    mLabelWifi.setText(mWifiMin + MINUTE);
                }
            }
        });

        mRsvOther.setOnSlideListener(new RangeSliderViewEx.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                //最后一段自定义时间
                int lastIndex = (mRsvOther.getRangeCount() - 1);
                if (index == lastIndex) {
                    mEtOther.setText(mOtherDay + "");
                    mEtOther.setVisibility(View.VISIBLE);
                    mEtOther.requestFocus();
                    mLabelOther.setText(DAY+ LABEL);
                } else {
                    mEtOther.setVisibility(View.GONE);
                    mOtherDay = (index + 1) * DAY_STEP;
                    mLabelOther.setText(mOtherDay + DAY);
                }
            }
        });
    }

    private void bindData() {
        boolean isOpen = Settings.getBoolean(Settings.CACHE_OVERTIME, false);
        showOrHideLayout(isOpen);
        mCacheSwitch.setOn(isOpen);

        int wifiOverTime = Settings.getInt(Settings.CACHE_OVERTIME_WIFI, 30);
        int otherOverTime = Settings.getInt(Settings.CACHE_OVERTIME_OTHER, 2);
        mWifiMin = wifiOverTime;
        mOtherDay = otherOverTime;

        bindWiFiRsv(wifiOverTime);
        bindDayRsv(otherOverTime);
    }

    private void bindDayRsv(int otherOverTime) {
        int dayStep = mOtherDay / DAY_STEP - 1;

        if (mOtherDay <= DAY_STEP * (mRsvOther.getRangeCount() - 1) && dayStep >= 0) {
            mRsvOther.setInitialIndex(dayStep);
            mLabelOther.setText(otherOverTime + DAY);
        } else {
            if (mOtherDay > DAY_STEP) {
                mRsvOther.setInitialIndex(mRsvOther.getRangeCount() - 1);
                mEtOther.setVisibility(View.VISIBLE);
            } else {
                mRsvOther.setInitialIndex(0);
            }
            mEtOther.setText(mOtherDay + "");
            mLabelOther.setText(DAY);
        }
    }

    private void bindWiFiRsv(int wifiOverTime) {
        int minStep = mWifiMin / MINUTE_STEP - 1;

        if (mWifiMin <= MINUTE_STEP * (mRsvWifi.getRangeCount() - 1) && minStep >= 0) {
            mRsvWifi.setInitialIndex(minStep);
            mLabelWifi.setText(wifiOverTime + MINUTE);
        } else {
            if (minStep > MINUTE_STEP) {
                mRsvWifi.setInitialIndex(mRsvWifi.getRangeCount() - 1);
                mEtWifi.setVisibility(View.VISIBLE);
            } else {
                mRsvWifi.setInitialIndex(0);
            }
            mEtWifi.setText(mWifiMin + "");

            mLabelWifi.setText(MINUTE);
        }
    }

    private void initView() {
        mWifiSwitchLayout = (RelativeLayout) findViewById(R.id.rl_wifi_cache);
        mOtherSwitchLayout = (RelativeLayout) findViewById(R.id.rl_other_cache);

        mLabelWifi = (TextView) findViewById(R.id.tv_label_min_wifi);
        mLabelOther = (TextView) findViewById(R.id.tv_label_min_other);

        mEtWifi = (EditText) findViewById(R.id.et_label_min_wifi);
        mEtOther = (EditText) findViewById(R.id.et_label_min_other);

        mRsvWifi = (RangeSliderViewEx) findViewById(R.id.rsv_wifi);
        mRsvOther = (RangeSliderViewEx) findViewById(R.id.rsv_other);

        mCacheSwitch = (ShSwitchView) findViewById(R.id.switch_view);

    }

    private void showOrHideLayout(boolean isOpen) {
        if (isOpen) {
            mWifiSwitchLayout.setVisibility(View.VISIBLE);
            mOtherSwitchLayout.setVisibility(View.VISIBLE);
        } else {
            mWifiSwitchLayout.setVisibility(View.INVISIBLE);
            mOtherSwitchLayout.setVisibility(View.INVISIBLE);
        }
    }
}
