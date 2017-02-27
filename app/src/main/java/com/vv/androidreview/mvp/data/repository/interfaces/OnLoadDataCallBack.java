package com.vv.androidreview.mvp.data.repository.interfaces;

/**
 * Created by Vv
 * 2016/9/21 0021.
 * Version 1.0
 * Description：
 */
public interface OnLoadDataCallBack<T> {
    /** 读取成功
     * @param data 数据
     */
    void onSuccess(T data);

    /** 失败
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    void onFail(int errorCode, String errorMsg);
}
