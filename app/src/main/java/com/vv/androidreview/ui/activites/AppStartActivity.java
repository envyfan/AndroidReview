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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vv.androidreview.R;
import com.vv.androidreview.base.system.AppManager;
import com.vv.androidreview.utils.PermissionsChecker;


public class AppStartActivity extends Activity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 休眠2秒

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        setContentView(R.layout.activity_app_start);

        mPermissionsChecker = new PermissionsChecker(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }else{
            enterMainDelay();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    private void enterMainDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    /**
     * 跳转到主Activity
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
