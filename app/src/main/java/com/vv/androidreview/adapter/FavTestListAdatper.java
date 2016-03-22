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

package com.vv.androidreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.base.MyBaseAdapter;
import com.vv.androidreview.entity.Test;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class FavTestListAdatper extends MyBaseAdapter<Test> {

    public FavTestListAdatper(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Test test = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_fav_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_test_question.setText(test.getQuestion());
        holder.tv_create_time.setText(test.getCreatedAt());

        return convertView;
    }

    private class ViewHolder{
        TextView tv_test_question,tv_create_time;

        public ViewHolder(View view) {
            tv_test_question = (TextView) view.findViewById(R.id.tv_test_question);
            tv_create_time = (TextView) view.findViewById(R.id.tv_create_time);
        }
    }
}
