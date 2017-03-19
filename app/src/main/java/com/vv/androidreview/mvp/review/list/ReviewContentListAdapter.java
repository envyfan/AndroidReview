package com.vv.androidreview.mvp.review.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.data.entity.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */

public class ReviewContentListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Content> mData = new ArrayList<>();

    public ReviewContentListAdapter(Context context) {
        this.mContext = context;
    }

    public void addData(List<Content> data) {
        if (mData != null && data != null && !data.isEmpty()) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setData(List<Content> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Content content = mData.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_content_title.setText(content.getTitle());
        viewHolder.tv_content_small.setText(content.getSmall());
        viewHolder.tv_create_time.setText(content.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content_title, tv_content_small, tv_create_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content_title = (TextView) itemView.findViewById(R.id.tv_content_title);
            tv_content_small = (TextView) itemView.findViewById(R.id.tv_content_small);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
        }
    }
}
