package com.vv.androidreview.mvp.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.orhanobut.logger.Logger;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.vv.androidreview.R;
import com.vv.androidreview.mvp.base.BaseToolbarActivity;
import com.vv.androidreview.mvp.exam.ExamFragment;
import com.vv.androidreview.mvp.review.ReviewFragment;
import com.vv.androidreview.mvp.review.ReviewPresenter;
import com.vv.androidreview.mvp.setting.SettingFragment;
import com.vv.androidreview.utils.DoubleClickExitHelper;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseToolbarActivity {

    private static final String TAG_REVIEW = "tag_review";
    private static final String TAG_EXAM = "tag_exam";
    private static final String TAG_SETTING = "tag_setting";

    private BottomBar mBottomBar;
    private Map<String,Fragment> mFragmentMap = new HashMap<>();
    private Fragment mLastFragment;

    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar(false, getString(R.string.app_name));

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
                        ReviewFragment reviewFragment = (ReviewFragment) getSupportFragmentManager().findFragmentByTag(TAG_REVIEW);
                        if (reviewFragment == null) {
                            reviewFragment = ReviewFragment.newInstance();
                            addFragmentToActivity(reviewFragment, R.id.frame_content, TAG_REVIEW);
                            ReviewPresenter reviewPresenter = new ReviewPresenter(MainActivity.this, reviewFragment, reviewFragment, reviewFragment);
                            reviewFragment.setPresenter(reviewPresenter);
                        }
                        doTabChanged(TAG_REVIEW);
                        break;
                    case R.id.bb_menu_exam:
                        ExamFragment examFragment = (ExamFragment) getSupportFragmentManager().findFragmentByTag(TAG_EXAM);
                        if(examFragment == null){
                            examFragment = ExamFragment.newInstance();
                            addFragmentToActivity(examFragment,R.id.frame_content,TAG_EXAM);
                        }
                        doTabChanged(TAG_EXAM);
                        break;
                    case R.id.bb_menu_setting:
                        SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager().findFragmentByTag(TAG_SETTING);
                        if(settingFragment == null){
                            settingFragment = SettingFragment.newInstance();
                            addFragmentToActivity(settingFragment, R.id.frame_content, TAG_SETTING);
                        }
                        doTabChanged(TAG_SETTING);
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

    // 切换菜单
    private void doTabChanged(String tag){
        Fragment newTab;
        newTab = mFragmentMap.get(tag);

        if (newTab == null) {
            Logger.d("No tab known for tag " + tag);
            throw new IllegalStateException("No tab known for tag " + tag);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mLastFragment != newTab) {
            if (mLastFragment != null) {
                ft.detach(mLastFragment);
            }

            ft.attach(newTab);
            mLastFragment = newTab;
            ft.commit();
        }
    }

    //添加Fragment到Activity
    private void addFragmentToActivity(@NonNull Fragment fragment, int frameId, @NonNull String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragmentMap.put(tag, fragment);
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

}
