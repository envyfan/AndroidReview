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
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.config.AppConfig;
import com.vv.androidreview.mvp.data.entity.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class ReviewListAdapter extends RecyclerView.Adapter {

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
    private List<Map<String, List<Point>>> mData = new ArrayList<>();
    private ItemAdapter mItemAdapter;

    public ReviewListAdapter(Context context) {
        this.mContext = context;
        mItemAdapter = new ItemAdapter();
    }

    public void setData(List<Map<String, List<Point>>> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*
            顺便复习，inflate 3个参数的含义
            1、resourceId 布局id
            2、root需要附加到resource资源文件的根控件，inflate()会返回一个View对象，如果第三个参数attachToRoot为true，
               就将这个root作为根对象返回，否则仅仅将这个root对象的LayoutParams属性附加到resource对象的根布局对象上，
               也就是布局文件resource的最外层的View上。如果root为null则会忽略view根对象的LayoutParams属性。
            3、attachToRoot 是否将root附加到布局文件的根视图上
         */
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_item, null);
        return new ItemRecycleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String, List<Point>> group = mData.get(position);
        ItemRecycleHolder itemRecycleHolder = (ItemRecycleHolder) holder;
        String unitName = "";
        if (group.keySet().iterator().hasNext()) {
            unitName = group.keySet().iterator().next();
        }
        itemRecycleHolder.tv_unit.setText(unitName);

        List<Point> points = group.get(unitName);
        mItemAdapter.setData(points);
        itemRecycleHolder.rv_carview.setLayoutManager(new GridLayoutManager(mContext, AppConfig.ReviewPageConfig.PAGE_REVIEW_LIST_COLUMN));
        itemRecycleHolder.rv_carview.setAdapter(mItemAdapter);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void startContentList(Point point) {
//        Intent intent = new Intent(mContext, ContentListActivity.class);
//        intent.putExtra(ContentListActivity.ARGUMENT_POINT_KEY, point);
//        mContext.startActivity(intent);
    }

    private class ItemAdapter extends RecyclerView.Adapter {

        private List<Point> points = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_list_review_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final Point point = points.get(position);
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.tv_carview.setText(point.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (point.getObjectId() != null) {
                        startContentList(point);
                        Toast.makeText(mContext, "on click ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cardViewSetBackgroundColor(point, itemHolder.cv_carview);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return points.size();
        }

        public void setData(List<Point> pooints) {
            this.points = pooints;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_carview;
        private CardView cv_carview;

        public ItemViewHolder(View view) {
            super(view);
            tv_carview = (TextView) view.findViewById(R.id.tv_cardview);
            cv_carview = (CardView) view.findViewById(R.id.cv_cardview);
        }
    }

    private class ItemRecycleHolder extends RecyclerView.ViewHolder {
        private TextView tv_unit;
        private RecyclerView rv_carview;

        public ItemRecycleHolder(View itemView) {
            super(itemView);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            rv_carview = (RecyclerView) itemView.findViewById(R.id.rv_cardview);
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
