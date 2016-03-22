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
