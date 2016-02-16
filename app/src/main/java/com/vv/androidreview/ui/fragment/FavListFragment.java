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
