package com.vv.androidreview.mvp.review.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;
import com.vv.androidreview.mvp.data.entity.Point;

public class ReviewContentListActivity extends BaseToolbarActivity {
    public static final String ARGUMENT_POINT_KEY = "argument_point_key";
    private Point mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_content_list);

        initArguments();
        initToolBar(true, mPoint != null ? mPoint.getName() : "");
        initView();
    }

    private void initView() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (fragment == null || !(fragment instanceof ReviewContentListFragment)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ReviewContentListFragment reviewContentListFragment = ReviewContentListFragment.newInstance();
            ReviewContentListPresenter reviewContentListPresenter = new ReviewContentListPresenter(this,
                    reviewContentListFragment,
                    reviewContentListFragment,
                    reviewContentListFragment,
                    mPoint);
            reviewContentListFragment.setPresenter(reviewContentListPresenter);
            transaction.add(R.id.fl_content, reviewContentListFragment);
            transaction.commit();
        }

    }

    private void initArguments() {
        Intent intent = getIntent();
        if (intent != null) {
            mPoint = (Point) intent.getSerializableExtra(ARGUMENT_POINT_KEY);
        }
    }
}
