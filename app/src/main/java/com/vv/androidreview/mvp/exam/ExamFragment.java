package com.vv.androidreview.mvp.exam;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseFragment;

public class ExamFragment extends BaseFragment implements ExamContract.View{

    public static ExamFragment newInstance() {

        Bundle args = new Bundle();
        ExamFragment fragment = new ExamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_exam;
    }

    @Override
    public void setPresenter(ExamContract.Presenter presenter) {

    }
}
