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

package com.vv.androidreview.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas = new ArrayList<>();

    protected Context mContext;

    public MyBaseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(List<T> datas) {
        if (mDatas != null && datas != null && !datas.isEmpty()) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void notifyAdapter(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas){
        this.mDatas = datas;
    }

}
