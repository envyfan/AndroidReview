package com.vv.androidreview.mvp.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;
import com.vv.androidreview.mvp.component.pulltorefresh.PullToRefreshPresenter;
import com.vv.androidreview.mvp.review.ReviewFragmentEx;
import com.vv.androidreview.ui.fragment.ReviewFragment;
import com.vv.androidreview.utils.DoubleClickExitHelper;

public class Main2Activity extends BaseToolbarActivity {

    private static final String TAG_REVIEW = "tag_review";
    private static final String TAG_EXAM = "tag_exam";
    private static final String TAG_SETTING = "tag_setting";

    private BottomBar mBottomBar;

    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initToolBar(false,getString(R.string.app_name));

        //初始化底部菜单导航条
        initBottomMenu(savedInstanceState);
        //双击退出帮助类
        mDoubleClickExit = new DoubleClickExitHelper(this);

    }

    //初始化底部菜单导航条
    private void initBottomMenu(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_menu);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //处理导航触发事件 复习、测试、其他
                switch (menuItemId) {
                    case R.id.bb_menu_review:
//                        ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentByTag(TAG_REVIEW);
//                        if(reviewFragment == null){
//                            reviewFragment = ReviewFragment.newInstance();
//                            addFragmentToActivity(reviewFragment,R.id.frame_content,TAG_REVIEW);
//                        }
//                        if(mReviewPresenter == null){
//                            Context context = MainActivity.this;
//                            mReviewPresenter = new ReviewPresenter(reviewFragment,context,
//                                    PointRepository.newInstance(context, PointRemoteDataSource.newInstance(context),
//                                            PointLocalDataSource.newInstance(context)));
//                        }
//                        doTabChanged(TAG_REVIEW);
                        ReviewFragmentEx reviewFragmentEx = (ReviewFragmentEx) getSupportFragmentManager().findFragmentByTag(TAG_REVIEW);
                        if (reviewFragmentEx == null) {
                            reviewFragmentEx = ReviewFragmentEx.newInstance();
                            addFragmentToActivity(reviewFragmentEx,R.id.frame_content,TAG_REVIEW);
                        }
                        PullToRefreshPresenter pullToRefreshPresenter = new PullToRefreshPresenter(reviewFragmentEx);
                        reviewFragmentEx.setRefreshPresenter(pullToRefreshPresenter);
                        break;
                    case R.id.bb_menu_exam:
//                        ExamFragment examFragment = (ExamFragment) mFragmentManager.findFragmentByTag(TAG_EXAM);
//                        if(examFragment == null){
//                            examFragment = ExamFragment.newInstance();
//                            addFragmentToActivity(examFragment,R.id.frame_content,TAG_EXAM);
//                        }
//                        doTabChanged(TAG_EXAM);
                        break;
                    case R.id.bb_menu_setting:
//                        SettingFragment settingFragment = (SettingFragment) mFragmentManager.findFragmentByTag(TAG_SETTING);
//                        if(settingFragment == null){
//                            settingFragment = SettingFragment.newInstance();
//                            addFragmentToActivity(settingFragment, R.id.frame_content, TAG_SETTING);
//                        }
//                        doTabChanged(TAG_SETTING);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //再次点击该菜单时触发此方法
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mBottomBar.onSaveInstanceState(outState);
    }

    //监听返回--是否退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
