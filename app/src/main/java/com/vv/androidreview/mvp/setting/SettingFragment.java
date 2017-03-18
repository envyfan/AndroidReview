package com.vv.androidreview.mvp.setting;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;
import com.vv.androidreview.mvp.exam.ExamContract;

public class SettingFragment extends BaseFragment implements SettingContract.View{

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting2;
    }

    @Override
    public void setPresenter(ExamContract.Presenter presenter) {

    }
}
