package com.vv.androidreview.mvp.base;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.vv.androidreview.R;

/**
 * Created by zhiwei.a.fan on 2/20/2017.
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    protected Toolbar mToolbar;

    /**
     * 隐藏ToolBar
     */
    public void hideToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * 显示ToolBar
     */
    public void showToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar(boolean isShowCancel) {
        initToolBar(-1, isShowCancel, "");
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar(boolean isShowCancel, String title) {
        initToolBar(-1, isShowCancel, title);
    }

    /**
     * 初始化ToolBar
     *
     * @param logoRes logo 资源id
     */
    public void initToolBar(int logoRes, boolean isShowCancel, String title) {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        if (logoRes > 0) {
            mToolbar.setLogo(logoRes);
        }
        mToolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
        mToolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);


        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        }

        setSupportActionBar(mToolbar);

        if (isShowCancel) {
            mToolbar.setNavigationIcon(R.mipmap.ic_top_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            mToolbar.setNavigationIcon(null);
            mToolbar.setNavigationOnClickListener(null);
        }


    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


}
