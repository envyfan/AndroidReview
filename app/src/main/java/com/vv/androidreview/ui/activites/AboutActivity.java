package com.vv.androidreview.ui.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private TextView mBtAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolBar();
        showOrHideToolBarNavigation(true);

        mBtAuthor = (TextView) findViewById(R.id.bt_about_author);
        mBtAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,AuthorActivity.class);
                startActivity(intent);
            }
        });
        setStatusBarCompat();
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.about);
    }
}
