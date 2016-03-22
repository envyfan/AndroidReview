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
public class ReadCacheAsyncTask<T> extends AsyncTask<String, Void, T> {
    private WeakReference<Context> mContext;

    public interface OnReadCacheToDo<T> {
        void preExecute();

        void postExectue(T data);
    }

    private OnReadCacheToDo<T> onReadCacheToDo;

    public void setOnReadCacheToDo(OnReadCacheToDo<T> onReadCacheToDo) {
        this.onReadCacheToDo = onReadCacheToDo;
    }

    public ReadCacheAsyncTask(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onReadCacheToDo != null) {
            onReadCacheToDo.preExecute();
        }
    }

    @Override
    protected T doInBackground(String... params) {
        if (mContext.get() != null) {
            Serializable seri = CacheHelper.readObject(mContext.get(), params[0]);
            if (seri == null) {
                return null;
            } else {
                return (T) seri;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);
        if (onReadCacheToDo != null) {
            onReadCacheToDo.postExectue(data);
        }
    }
}
