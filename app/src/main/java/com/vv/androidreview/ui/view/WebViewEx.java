package com.vv.androidreview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class WebViewEx extends WebView {
    public WebViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public WebViewEx(Context context) {
        super(context);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

}

