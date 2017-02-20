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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;
import com.vv.androidreview.ui.fragment.Indicator;
import com.vv.androidreview.utils.DoubleClickExitHelper;

public class MainActivity extends BaseToolbarActivity {

    private DoubleClickExitHelper mDoubleClickExit;
    private FragmentTabHost mFragmentTabHost;
    protected TextView mCount;
    private View mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        setContentView(mRootView);

        initToolBar(false,getString(R.string.app_name));
        initView();
        setStatusBarCompat();
    }

    private void initView() {
        //测试栏目的题目统计TextView
        mCount = (TextView) findViewById(R.id.tv_count);
        mDoubleClickExit = new DoubleClickExitHelper(this);

        Indicator[] indicators = Indicator.values();
        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realtabcontent);

        //初始化Tab
        for (int i = 0; i < indicators.length; i++){
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(getString(indicators[i].getResName()));
            tabSpec.setIndicator(getIndicatorView(indicators[i]));
            mFragmentTabHost.addTab(tabSpec, indicators[i].getClz(), null);
        }
        //去除底部按钮之间的分割线
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mFragmentTabHost.getTabWidget().setShowDividers(0);

            mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    if(tabId.equals(getString(Indicator.TEST.getResName()))){
                        mCount.setVisibility(View.VISIBLE);
                    }else{
                        mCount.setVisibility(View.GONE);
                    }
                }
            });
    }}

    public View getRootView() {
        return mRootView;
    }


    public void setTextCount(int count){
        mCount.setText("已做"+count+"题");
    }

    /** 返回设置好的底部按钮
     * @param indicator1
     * @return
     */
    private View getIndicatorView(Indicator indicator1) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        TextView indicator = (TextView) view.findViewById(R.id.tab_title);

        indicator.setText(getString(indicator1.getResName()));

        indicator.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        Drawable icon = this.getResources().getDrawable(indicator1.getResIcon());
//        自定义ICON大小
//        icon.setBounds(0, 0, 75, 75);
//        indicator.setCompoundDrawables(null,icon,null,null);
        indicator.setCompoundDrawablePadding(3);
        indicator.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        indicator.setPadding(0,8,0,5);


        return view;
    }


    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
