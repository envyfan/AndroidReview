package com.vv.androidreview.cache;

import android.content.Context;
import android.os.AsyncTask;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class SaveCacheAsyncTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Context> mContext;
    private Serializable seri;
    private String cacheKey;

    public SaveCacheAsyncTask(Context context, Serializable seri, String cacheKey) {
        mContext = new WeakReference<Context>(context);
        this.seri = seri;
        this.cacheKey = cacheKey;
    }

    @Override
    protected Void doInBackground(Void... params) {
        CacheHelper.saveObject(mContext.get(), seri, cacheKey);
        return null;
    }


}
