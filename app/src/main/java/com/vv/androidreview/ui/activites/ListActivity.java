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

package com.vv.androidreview.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;
import com.vv.androidreview.entity.Point;
import com.vv.androidreview.ui.fragment.FavListFragment;
import com.vv.androidreview.ui.fragment.ReviewContentListFragment;
import com.vv.androidreview.ui.fragment.ReviewFragment;

public class ListActivity extends BaseActivity {
    public static final String CONTENT_TYPE_KEY = "content_type_key";

    public static final int LIST_TYPE_REVIEW_CONTENT = 1;
    public static final int LIST_TYPE_FAV_TEST = 2;

    private int mType = -1;

    private ReviewContentListFragment mReviewContentListFragment;
    private FavListFragment mFavListFragment;

    private Point mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initArguments();
        initToolBar();
        showOrHideToolBarNavigation(true);
        initView();
        setStatusBarCompat();
    }

    private void initArguments() {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra(CONTENT_TYPE_KEY, -1);

            switch (mType) {
                case LIST_TYPE_REVIEW_CONTENT:
                    mPoint = (Point) intent.getSerializableExtra(ReviewFragment.ARGUMENT_POINT_KEY);
                    break;
                case LIST_TYPE_FAV_TEST:
                    break;
            }
        }
    }

    private void initView() {
        switch (mType) {
            case LIST_TYPE_REVIEW_CONTENT:
                showReviewContentListFragment();
                break;
            case LIST_TYPE_FAV_TEST:
                showFavListFragment();
                break;
        }
    }

    private void showFavListFragment(){
        mFavListFragment = new FavListFragment();
        Bundle bundle = new Bundle();
        mFavListFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_content_fragment, mFavListFragment);
        fragmentTransaction.commit();

    }

    private void showReviewContentListFragment() {
        mReviewContentListFragment = new ReviewContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ReviewFragment.ARGUMENT_POINT_KEY, mPoint);
        mReviewContentListFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_content_fragment, mReviewContentListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public String returnToolBarTitle() {
        switch (mType) {
            case LIST_TYPE_REVIEW_CONTENT:
                return mPoint.getName();
            case LIST_TYPE_FAV_TEST:
                return getString(R.string.my_fav);
            default:
                return getString(R.string.app_name);
        }
    }
}
