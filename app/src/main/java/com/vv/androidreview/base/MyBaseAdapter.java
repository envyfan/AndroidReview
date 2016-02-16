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
