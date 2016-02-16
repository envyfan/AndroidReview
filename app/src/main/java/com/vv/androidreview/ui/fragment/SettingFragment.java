package com.vv.androidreview.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;
import com.vv.androidreview.base.BaseFragment;
import com.vv.androidreview.entity.Suggest;
import com.vv.androidreview.ui.activites.AboutActivity;
import com.vv.androidreview.ui.activites.ListActivity;
import com.vv.androidreview.ui.activites.MainActivity;
import com.vv.androidreview.ui.activites.SuggestActivity;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class SettingFragment extends BaseFragment{
    private View mRootView;
    private RelativeLayout mBtSuggest,mBtUpdate,mBtAbout,mBtFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
            creatViews();
        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;

    }

    private void creatViews() {
        mBtSuggest = (RelativeLayout) mRootView.findViewById(R.id.bt_suggest);
        mBtUpdate = (RelativeLayout) mRootView.findViewById(R.id.bt_update);
        mBtAbout = (RelativeLayout) mRootView.findViewById(R.id.bt_about);
        mBtFav = (RelativeLayout) mRootView.findViewById(R.id.bt_my_fav);

        BtClickListener clickListener = new BtClickListener();
        mBtSuggest.setOnClickListener(clickListener);
        mBtUpdate.setOnClickListener(clickListener);
        mBtAbout.setOnClickListener(clickListener);
        mBtFav.setOnClickListener(clickListener);

    }

    private class BtClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()){
                case R.id.bt_suggest:
                    intent.setClass(getContext(), SuggestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_update:
                    break;
                case R.id.bt_about:
                    intent.setClass(getContext(), AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_my_fav:
                    intent.setClass(getContext(), ListActivity.class);
                    intent.putExtra(ListActivity.CONTENT_TYPE_KEY,ListActivity.LIST_TYPE_FAV_TEST);
                    startActivity(intent);
                    break;
            }
        }
    }
}
