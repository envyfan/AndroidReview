package com.vv.androidreview.ui.activites;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vv.androidreview.R;
import com.vv.androidreview.base.BaseActivity;
import com.vv.androidreview.entity.Suggest;

import cn.bmob.v3.listener.SaveListener;

public class SuggestActivity extends BaseActivity {
    private TextInputLayout mTiSuggest,mTiMailOrQq;
    private View mRootView;
    //防止多次提交数据
    private boolean isPosting=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_suggest,null);
        setContentView(mRootView);
        initToolBar();
        showOrHideToolBarNavigation(true);
        setStatusBarCompat();

        mTiSuggest = (TextInputLayout) findViewById(R.id.ti_suggest);
        mTiMailOrQq = (TextInputLayout) findViewById(R.id.ti_email_or_qq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suggest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_submit:
                String msg = mTiSuggest.getEditText().getText().toString();
                if(TextUtils.isEmpty(msg)){
                    Snackbar.make(mRootView, R.string.dont_no_text, Snackbar.LENGTH_SHORT).show();
                }else{
                    Suggest suggest = new Suggest();
                    suggest.setMsg(msg);
                    suggest.setMail_qq(mTiMailOrQq.getEditText().getText().toString());
                    if(!isPosting) {
                        isPosting = true;
                        suggest.save(SuggestActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                isPosting = false;
                                Toast.makeText(SuggestActivity.this, R.string.thx, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                isPosting = false;
                                Snackbar.make(mRootView, R.string.sugesst_error, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Snackbar.make(mRootView, R.string.dont_repeat, Snackbar.LENGTH_SHORT).show();
                    }
                }

                break;

            default:
                break;
        }
//         Toast.makeText(MainActivity.this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String returnToolBarTitle() {
        return getString(R.string.suggest);
    }
}
