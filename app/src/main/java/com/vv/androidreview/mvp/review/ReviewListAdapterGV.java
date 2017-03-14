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

package com.vv.androidreview.mvp.review;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.data.entity.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 * item里嵌套 GridView
 */
public class ReviewListAdapterGV extends RecyclerView.Adapter{
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

    protected Context mContext;
    private List<Map<String, List<Point>>> mDatas = new ArrayList<>();

    public ReviewListAdapterGV(Context context) {
        this.mContext = context;
    }

    public void setDatas(List<Map<String, List<Point>>> datas){
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*
            顺便复习，inflate 3个参数的含义
            1、resouceId 布局id
            2、root需要附加到resource资源文件的根控件，inflate()会返回一个View对象，如果第三个参数attachToRoot为true，
               就将这个root作为根对象返回，否则仅仅将这个root对象的LayoutParams属性附加到resource对象的根布局对象上，
               也就是布局文件resource的最外层的View上。如果root为null则会忽略view根对象的LayoutParams属性。
            3、attachToRoot 是否将root附加到布局文件的根视图上
         */
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_item_gv,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String, List<Point>> gruop = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        String unitName = "";
        if (gruop.keySet().iterator().hasNext()) {
            unitName = gruop.keySet().iterator().next();
        }
        viewHolder.tv_unit.setText(unitName);
        List<Point> points = gruop.get(unitName);
        viewHolder.gv_carview.setAdapter(new GvAdapter(points));
        //取消gridview默认的点击效果 只留下carview的点击效果
        viewHolder.gv_carview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        viewHolder.gv_carview.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Point point = (Point) parent.getItemAtPosition(position);
                if (point.getObjectId() != null) {
                    startContentList(point);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_unit;
        private GridView gv_carview;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            gv_carview = (GridView) itemView.findViewById(R.id.gv_carview);
        }
    }

    private void startContentList(Point point) {
//        Intent intent = new Intent(mContext, ContentListActivity.class);
//        intent.putExtra(ContentListActivity.ARGUMENT_POINT_KEY, point);
//        mContext.startActivity(intent);
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
}
