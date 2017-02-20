package com.vv.androidreview.mvp.base;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.vv.androidreview.R;

/**
 * Created by zhiwei.a.fan on 2/20/2017.
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;

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
        initToolBar(-1, isShowCancel,"");
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar(boolean isShowCancel,String title) {
        initToolBar(-1, isShowCancel,title);
    }

    /**
     * 初始化ToolBar
     *
     * @param logoRes logo 资源id
     */
    public void initToolBar(int logoRes, boolean isShowCancel,String title) {
        toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        if (toolbar != null) {
            if (logoRes > 0) {
                toolbar.setLogo(logoRes);
            }
            toolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            toolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(toolbar);
        }

        if (isShowCancel) {
            toolbar.setNavigationIcon(R.mipmap.ic_top_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        }

        if (!TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


}
