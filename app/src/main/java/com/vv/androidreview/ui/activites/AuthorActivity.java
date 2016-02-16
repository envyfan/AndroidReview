package com.vv.androidreview.ui.activites;

import android.app.Activity;
import android.os.Bundle;

import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;

public class AuthorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        initToolBar();
        showOrHideToolBarNavigation(true);
        setStatusBarCompat();
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.author);

    }
}
