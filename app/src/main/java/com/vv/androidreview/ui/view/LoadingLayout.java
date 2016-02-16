package com.vv.androidreview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vv.androidreview.R;

/**
 * Author：Vv on .
 * Mail：envyfan@qq.com
 * Description：
 */
public class LoadingLayout extends LinearLayout {

    public final static int STATE_REFRESH = 2;
    public final static int STATE_NONE = 1;

    public static final int HIDE_LAYOUT = -0;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NETWORK_REFRESH = 3;
    public static final int LOADDATA_ERROR = 4;
    public static final int NO_DATA_FAV = 5;

    public static int sState = STATE_NONE;


    private Context mContext;
    private ImageView mErrorImage, mRefreshImage;
    private TextView mTextView;
    private ProgressBar mAnimProgress;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setEnabled(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.loading,this,false);
        mErrorImage = (ImageView) view.findViewById(R.id.img_error);
        mRefreshImage = (ImageView) view.findViewById(R.id.img_refresh);
        mTextView = (TextView) view.findViewById(R.id.tv_tip);
        mAnimProgress = (ProgressBar) view.findViewById(R.id.animProgress);

        addView(view);
    }

    public int getState(){
        return sState;
    }

    public void setLoadingLayout(int type) {
        switch (type) {
            case HIDE_LAYOUT:
                setEnabled(false);
                sState = STATE_NONE;
                setVisibility(View.GONE);

                break;
            case NETWORK_ERROR:
                setEnabled(true);
                sState = STATE_NONE;
                setVisibility(View.VISIBLE);
                mErrorImage.setVisibility(View.VISIBLE);
                mRefreshImage.setVisibility(View.GONE);
                mAnimProgress.setVisibility(View.GONE);

                mTextView.setText("网络不太好哦，请点击重新加载");
                break;
            case NETWORK_LOADING:
                setEnabled(false);
                sState = STATE_REFRESH;
                setVisibility(View.VISIBLE);
                mErrorImage.setVisibility(View.GONE);
                mRefreshImage.setVisibility(View.GONE);
                mAnimProgress.setVisibility(View.VISIBLE);

                mTextView.setText("加载中");
                break;
            case NETWORK_REFRESH:
                setEnabled(true);
                sState = STATE_NONE;
                setVisibility(View.VISIBLE);
                mErrorImage.setVisibility(View.GONE);
                mRefreshImage.setVisibility(View.VISIBLE);
                mAnimProgress.setVisibility(View.GONE);
                mTextView.setText("暂无数据,刷新可能会有更新哦");
                break;
            case LOADDATA_ERROR:
                setEnabled(true);
                sState = STATE_NONE;
                setVisibility(View.VISIBLE);
                mErrorImage.setVisibility(View.GONE);
                mRefreshImage.setVisibility(View.VISIBLE);
                mAnimProgress.setVisibility(View.GONE);
                mTextView.setText("数据加载失败,请点击重新加载");
                break;

            case NO_DATA_FAV:
                setEnabled(true);
                sState = STATE_NONE;
                setVisibility(View.VISIBLE);
                mErrorImage.setVisibility(View.GONE);
                mRefreshImage.setVisibility(View.VISIBLE);
                mAnimProgress.setVisibility(View.GONE);
                mTextView.setText("你还没有收藏任何题目哦");
                break;
        }
    }
}
