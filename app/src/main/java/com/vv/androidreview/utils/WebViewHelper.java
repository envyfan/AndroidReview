package com.vv.androidreview.utils;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import com.orhanobut.logger.Logger;
import com.vv.androidreview.entity.Content;

import java.util.List;
import java.util.Map;

/**
 * Author：Vv on 2015/7/21 16:20
 * Mail：envyfan@qq.com
 * Description：UI帮助类
 */
public class WebViewHelper {

    public static final String LinkCss = "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">";
    public static final String LinkJS = "<script type=\"text/javascript\" src=\"file:///android_asset/js/common.js\"></script>";
    public static final String Function_RemoveImageAttribute="<script> javascript:removeImageAttribute()</script>";

    public static void initWebViewSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        //这个单位是SP
        settings.setDefaultFontSize(15);

        settings.setJavaScriptEnabled(true);  //支持js

        settings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

        settings.setSupportZoom(true);  //支持缩放

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使超链接失效
                return true;
            }
        });

    }

    /**
     * 拼凑HTML的头部
     *
     * @return
     */
    public static String getWebViewHead() {
        StringBuffer htmlHead = new StringBuffer();
        htmlHead.append("<head>");
        htmlHead.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        htmlHead.append(WebViewHelper.setWebViewPadding(2, 2));
        htmlHead.append(WebViewHelper.LinkCss);
        htmlHead.append(WebViewHelper.LinkJS);
        htmlHead.append("</head>");
        return htmlHead.toString();
    }

    public static String getWebViewHtml(Content content) {
        StringBuffer htmlBody = new StringBuffer();
        String body = content.getContent();
        htmlBody.append(getWebViewHead());
        htmlBody.append("<body>");
        htmlBody.append("<div>");
        htmlBody.append(body);
        htmlBody.append("</div>");
        htmlBody.append(Function_RemoveImageAttribute);
        htmlBody.append("</body>");
        return htmlBody.toString();
    }

    /**
     * 全局DIV标签CSS样式
     *
     * @return
     */
    public static String setWebViewPadding(int left, int right, int top) {
        String p = "<style type=\"text/css\">\n" +
                "  div {" +
                "       padding-left:" + TDevice.dpToPixel(left) + "px;" +
                "       padding-right:" + TDevice.dpToPixel(right) + "px;" +
                "       padding-top:" + TDevice.dpToPixel(top) + "px;" +
                "}\n" +
                "</style>";
        return p;
    }

    public static String setWebViewPadding(int rt, int top) {
        return setWebViewPadding(rt, rt, top);
    }

}
