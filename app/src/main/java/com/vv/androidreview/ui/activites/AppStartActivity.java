package com.vv.androidreview.ui.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vv.androidreview.R;
import com.vv.androidreview.base.system.AppManager;


public class AppStartActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 休眠2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        setContentView(R.layout.activity_app_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        },SPLASH_DISPLAY_LENGHT);

    }

    /**
     * 跳转到主Activity
     */
    private void redirectTo() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }


}
