package com.vv.androidreview.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author：Vv on 2016/1/17.
 * Mail：envyfan@qq.com
 * Description：
 */
public class ToastHelper {

    public static void showString(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
