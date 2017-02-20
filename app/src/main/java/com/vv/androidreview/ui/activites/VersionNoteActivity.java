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

package com.vv.androidreview.ui.activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.entity.VersionNote;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

public class VersionNoteActivity extends BaseToolbarActivity {

    private ListView mListView;

    private List<VersionNote> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_note);
        initToolBar(true, getString(R.string.update_text));
        mListView = (ListView) findViewById(R.id.listview);
        initDatas();
        mListView.setAdapter(new VersionNoteAdapter());
    }

    private void initDatas() {
        VersionNote v1 = new VersionNote("V1.0.0", "2016.02.22", "1、版本首发");
        mData.add(v1);
        VersionNote v2 = new VersionNote("V1.0.1", "2016.03.09", "1、修正了某些机型不能访问网络（6.0权限问题）" + "\n"
                + "2、优化了首页界面,改为GridView" + "\n"
                + "3、添加正文缓存有效期控制，默认情况下关闭有效期（即缓存永不过期）" + "\n"
                + "4、添加一件建表，方便阅读源码的朋友更易上手（发布版此功能隐藏）");
        mData.add(v2);
    }

    private class VersionNoteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VersionNote versionNote = mData.get(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(VersionNoteActivity.this).inflate(R.layout.itme_note_text, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.versionName.setText(versionNote.getVersionName());
            holder.updateTime.setText(versionNote.getUpdateTime());
            holder.versionNote.setText(versionNote.getUpdateNote());
            return convertView;
        }

        private class ViewHolder {
            private TextView versionName, updateTime, versionNote;

            public ViewHolder(View itemView) {
                versionName = (TextView) itemView.findViewById(R.id.tv_version_name);
                updateTime = (TextView) itemView.findViewById(R.id.tv_version_date);
                versionNote = (TextView) itemView.findViewById(R.id.tv_version_note);
            }
        }
    }
}
