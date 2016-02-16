package com.vv.androidreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.base.MyBaseAdapter;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.ui.activites.ListActivity;
import com.vv.androidreview.ui.fragment.ReviewFragment;
import com.vv.androidreview.utils.TDevice;

import java.util.List;
import java.util.Map;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 * 复习列表的适配器
 */
public class ReviewListAdapter extends MyBaseAdapter<Map<String, List<Point>>> {
    public static final int NO_CONTENT = 0;
    public static final int BROWN = 1;
    public static final int DEEP_ORANGE = 2;
    public static final int ORANGE = 3;
    public static final int GREEN = 4;
    public static final int LIGHT_BLUE = 5;
    public static final int PURPLE = 6;
    public static final int RED = 7;
    public static final int PINK = 8;

    public ReviewListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, List<Point>> gruop = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_review_item, null, false);
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
        //移除所有carview不然 复用converView时 会留有上一手生成的这个动态carview。 这里感觉可以不用写holder复用converview，因为内容不多。
        holder.ly_carview.removeAllViews();
        //根据不同的单元动态生成N个carview。
        createCardView(holder, points);
        return convertView;
    }

    private void createCardView(ViewHolder holder, List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            final Point point = points.get(i);
            View pointView = LayoutInflater.from(mContext).inflate(R.layout.carview_review, holder.ly_carview, false);
            TextView pointName = (TextView) pointView.findViewById(R.id.tv_carview);
            CardView cardView = (CardView) pointView.findViewById(R.id.cv_carview);
            //5.0CarView 才支持设置阴影
            if(Build.VERSION.SDK_INT >=21) {
                cardView.setElevation(TDevice.dpToPixel(8));
            }
            cardViewSetBackgroundColor(point, cardView);
            //如果不是无效知识点，则加入点击事件
            if (point.getObjectId() != null) {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startContentList(point);
                    }
                });
            }
            pointName.setText(point.getName());
            holder.ly_carview.addView(pointView);
        }
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
            default:
                cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.theme_color_level2));
                break;
        }
    }

    private class ViewHolder {
        private TextView tv_unit;
        private LinearLayout ly_carview;

        public ViewHolder(View view) {
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            ly_carview = (LinearLayout) view.findViewById(R.id.ly_carview);
        }
    }
}
