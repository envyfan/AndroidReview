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

package com.vv.androidreview.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vv.androidreview.R;
import com.vv.androidreview.adapter.FavTestListAdatper;
import com.vv.androidreview.base.BaseFragment;
import com.vv.androidreview.entity.Test;
import com.vv.androidreview.ui.activites.FavActivity;
import com.vv.androidreview.ui.view.LoadingLayout;
import com.vv.androidreview.cache.CacheHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class FavListFragment extends BaseFragment {
    private View mRootView;
    private ListView mListView;
    private LoadingLayout mLoadingLayout;
    private List<Test> mData = new ArrayList<>();
    private FavTestListAdatper mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_fav_list, container, false);
            creatViews();
            loadData();
        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;

    }

    private void loadData() {
        new LoadFavTestAsyncTask().execute();
    }


    private void creatViews() {
        mListView = (ListView) mRootView.findViewById(R.id.listview);
        mLoadingLayout = (LoadingLayout) mRootView.findViewById(R.id.ly_loading);
        mLoadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadFavTestAsyncTask().execute();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itent = new Intent(getContext(), FavActivity.class);
                itent.putExtra(FavActivity.FAV_KEY,mData.get(position));
                startActivity(itent);
            }
        });
    }

    private class LoadFavTestAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingLayout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
            mListView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sp = CacheHelper.getPreferences(CacheHelper.FAV);
            Map<String, ?> map = sp.getAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                if (entry.getValue() instanceof Integer) {
                    Integer testId = (Integer) entry.getValue();
                    mData.add(readTestCache(testId));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = new FavTestListAdatper(getContext());
            mAdapter.setDatas(mData);
            mListView.setAdapter(mAdapter);
            if(mData.size()!=0) {
                mLoadingLayout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
                mListView.setVisibility(View.VISIBLE);
            }else{
                mLoadingLayout.setLoadingLayout(LoadingLayout.NO_DATA_FAV);
                mListView.setVisibility(View.GONE);
            }
        }
    }

    private Test readTestCache(int testId) {
        Serializable seri = CacheHelper.readObject(getContext(), CacheHelper.TEST + testId);
        if (seri == null) {
            return null;
        } else {
            return (Test) seri;
        }
    }

}
