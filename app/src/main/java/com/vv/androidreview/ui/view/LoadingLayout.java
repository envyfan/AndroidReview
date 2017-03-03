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

package com.vv.androidreview.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.system.CodeConfig;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class LoadingLayout extends LinearLayout {

    public final static int STATE_REFRESH = 2;
    public final static int STATE_NONE = 1;

    public static final int HIDE_LAYOUT = 0;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NETWORK_REFRESH = 3;
    public static final int LOADDATA_ERROR = 4;
    public static final int NO_DATA_FAV = 5;

    public static int sState = STATE_NONE;

    private ImageView mRefreshImage;
    private TextView mTextView;
    private ProgressBar mAnimProgress;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setEnabled(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading, this, false);
        mRefreshImage = (ImageView) view.findViewById(R.id.img_refresh);
        mTextView = (TextView) view.findViewById(R.id.tv_tip);
        mAnimProgress = (ProgressBar) view.findViewById(R.id.animProgress);

        addView(view);
    }

    public int getState() {
        return sState;
    }

    /**
     * 设置加载布局显示内容（使用默认提示信息）
     *
     * @param type 显示类型
     *             HIDE_LAYOUT ：隐藏布局
     *             NETWORK_ERROR：　网络加载错误
     *             NETWORK_LOADING：　网络加载中
     *             NETWORK_REFRESH：　没有更多数据，点击再加载
     *             LOADDATA_ERROR：　加载失败（指本地加载失败）
     */
    public void setLoadingLayout(int type) {
        setLoadingLayout(type, "");
    }

    /**
     * 设置加载布局显示内容(提示信息使用默认提示)
     *
     * @param type 显示类型
     *             HIDE_LAYOUT ：隐藏布局
     *             NETWORK_ERROR：　网络加载错误
     *             NETWORK_LOADING：　网络加载中
     *             NETWORK_REFRESH：　没有更多数据，点击再加载
     *             LOADDATA_ERROR：　加载失败（指本地加载失败）
     */
    public void setLoadingLayout(int type, String msg) {

        if (type == CodeConfig.LoadingLayoutConfig.LAYOUT_NULL) {
            return;
        }

        String msgStr = "";
        switch (type) {
            case HIDE_LAYOUT:
                setEnabled(false);
                sState = STATE_NONE;
                setVisibility(View.GONE);
                break;
            case NETWORK_ERROR:
                setEnabled(true);
                sState = STATE_NONE;
                showRefreshImage();
                msgStr = "网络不太好哦，请点击重新加载";
                break;
            case NETWORK_LOADING:
                setEnabled(false);
                sState = STATE_REFRESH;
                showLoading();
                msgStr = "加载中";
                break;
            case NETWORK_REFRESH:
                setEnabled(true);
                sState = STATE_NONE;
                showRefreshImage();
                msgStr = "暂无数据,刷新可能会有更新哦";
                break;
            case LOADDATA_ERROR:
                setEnabled(true);
                sState = STATE_NONE;
                setVisibility(View.VISIBLE);
                showRefreshImage();
                msgStr = "数据加载失败,请点击重新加载";
                break;
            case NO_DATA_FAV:
                setEnabled(true);
                sState = STATE_NONE;
                showRefreshImage();
                msgStr = "你还没有收藏任何题目哦";
                break;
        }

        if (TextUtils.isEmpty(msg)) {
            mTextView.setText(msgStr);
        } else {
            mTextView.setText(msg);
        }

    }

    private void showRefreshImage() {
        setVisibility(View.VISIBLE);
        mRefreshImage.setVisibility(View.VISIBLE);
        mAnimProgress.setVisibility(View.GONE);
    }

    private void showLoading() {
        setVisibility(View.VISIBLE);
        mRefreshImage.setVisibility(View.GONE);
        mAnimProgress.setVisibility(View.VISIBLE);
    }
}
