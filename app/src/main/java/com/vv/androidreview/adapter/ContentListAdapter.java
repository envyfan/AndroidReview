package com.vv.androidreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.base.MyBaseAdapter;
import com.vv.androidreview.entity.Content;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class ContentListAdapter extends MyBaseAdapter<Content> {


    public ContentListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Content content = mDatas.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_content_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content_title.setText(content.getTitle());
        holder.tv_content_small.setText(content.getSmall());
        holder.tv_create_time.setText(content.getCreatedAt());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_content_title, tv_content_small, tv_create_time;

        public ViewHolder(View view) {
            tv_content_title = (TextView) view.findViewById(R.id.tv_content_title);
            tv_content_small = (TextView) view.findViewById(R.id.tv_content_small);
            tv_create_time = (TextView) view.findViewById(R.id.tv_create_time);
        }
    }
}
