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
