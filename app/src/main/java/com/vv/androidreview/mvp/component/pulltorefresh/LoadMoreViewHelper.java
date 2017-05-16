package com.vv.androidreview.mvp.component.pulltorefresh;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vv.androidreview.R;

/**
 * Created by Vv
 * 2016/3/21 0021.
 * email:envyfan@qq.com
 * Version 1.0
 * Description：
 * 构建footview帮助类
 */
public class LoadMoreViewHelper {

    protected View mFootView;

    protected TextView footerTv;
    protected ProgressBar footerBar;

    protected View.OnClickListener onClickRefreshListener;

    public LoadMoreViewHelper(View footView, View.OnClickListener onClickRefreshListener) {
        mFootView = footView;
        footerTv = (TextView) mFootView.findViewById(R.id.load_more_default_footer_tv);
        footerBar = (ProgressBar) mFootView.findViewById(R.id.load_more_default_footer_progressbar);
        this.onClickRefreshListener = onClickRefreshListener;
        showNormal();
    }

    public View getFootView() {
        return mFootView;
    }

    public void showNormal() {
        footerTv.setVisibility(View.INVISIBLE);
//        footerTv.setText("点击加载更多");
        footerBar.setVisibility(View.GONE);
//        mFootView.setOnClickListener(onClickRefreshListener);
    }

    public void showLoading() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("正在加载中...");
        footerBar.setVisibility(View.VISIBLE);
        mFootView.setOnClickListener(null);
    }

    public void showFail() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("加载失败，点击重新");
        footerBar.setVisibility(View.GONE);
        mFootView.setOnClickListener(onClickRefreshListener);
    }

    public void showNoMore() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("已经加载完毕");
        footerBar.setVisibility(View.GONE);
        mFootView.setOnClickListener(onClickRefreshListener);
    }

    public void showNormalClick() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("点击加载更多");
        footerBar.setVisibility(View.GONE);
        mFootView.setOnClickListener(onClickRefreshListener);
    }
}
