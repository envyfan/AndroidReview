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
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.base.MyBaseAdapter;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.ui.activites.ListActivity;
import com.vv.androidreview.ui.fragment.ReviewFragment;
import com.vv.androidreview.utils.TDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 * 复习列表的适配器 （标题+GridView）
 */
public class ReviewListAdapterGV extends MyBaseAdapter<Map<String, List<Point>>> {
    public static final int DEFAULT = -1;
    public static final int NO_CONTENT = 0;
    public static final int BROWN = 1;
    public static final int DEEP_ORANGE = 2;
    public static final int ORANGE = 3;
    public static final int GREEN = 4;
    public static final int LIGHT_BLUE = 5;
    public static final int PURPLE = 6;
    public static final int RED = 7;
    public static final int PINK = 8;

    public ReviewListAdapterGV(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, List<Point>> gruop = mDatas.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_review_item_gv, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String unitName = "";
        if (gruop.keySet().iterator().hasNext()) {
            unitName = gruop.keySet().iterator().next();
        }
        holder.tv_unit.setText(unitName);
        List<Point> points = gruop.get(unitName);
        holder.gv_carview.setAdapter(new GvAdapter(points));
            holder.gv_carview.setOnItemClickListener(new GridView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Point point = (Point) parent.getItemAtPosition(position);
                    if (point.getObjectId() != null) {
                        startContentList(point);
                    }
                }
            });
        return convertView;
    }

    private void startContentList(Point point) {
        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(ReviewFragment.ARGUMENT_POINT_KEY, point);
        intent.putExtra(ListActivity.CONTENT_TYPE_KEY, ListActivity.LIST_TYPE_REVIEW_CONTENT);
        mContext.startActivity(intent);
    }

    private void cardViewSetBackgroundColor(Point point, CardView cardView) {
        switch (point.getColor()) {
            case NO_CONTENT:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.carview_no_content));
                break;
            case BROWN:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.brown_500));
                break;
            case DEEP_ORANGE:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.deep_orange_900));
                break;
            case ORANGE:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange_900));
                break;
            case GREEN:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case LIGHT_BLUE:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.light_blue_900));
                break;
            case RED:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.red_900));
                break;
            case PINK:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink_600));
                break;
            case PURPLE:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.purple_800));
                break;
            case DEFAULT:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.theme_color_level2));
                break;
        }
    }

    private class ViewHolder {
        private TextView tv_unit;
        private GridView gv_carview;

        public ViewHolder(View view) {
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            gv_carview = (GridView) view.findViewById(R.id.gv_carview);
        }
    }

    private class GvAdapter extends BaseAdapter {

        private List<Point> points = new ArrayList<>();

        public GvAdapter(List<Point> points) {
            this.points = points;
        }

        @Override
        public int getCount() {
            return points.size();
        }

        @Override
        public Object getItem(int position) {
            return points.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Point point = points.get(position);
            ViewHolder_GV viewHolder_gv = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.carview_review, null);
                viewHolder_gv = new ViewHolder_GV(convertView);
                convertView.setTag(viewHolder_gv);

            } else {
                viewHolder_gv = (ViewHolder_GV) convertView.getTag();
            }

            viewHolder_gv.tv_carview.setText(point.getName());
            cardViewSetBackgroundColor(point, viewHolder_gv.cv_carview);

            return convertView;
        }
    }

    private class ViewHolder_GV {
        private TextView tv_carview;
        private CardView cv_carview;

        public ViewHolder_GV(View view) {
            tv_carview = (TextView) view.findViewById(R.id.tv_carview);
            cv_carview = (CardView) view.findViewById(R.id.cv_carview);
        }
    }
}
