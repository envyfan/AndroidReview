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

package com.vv.androidreview.base.system;


import com.orhanobut.logger.Logger;
import com.vv.androidreview.base.BaseApplication;

import cn.bmob.v3.Bmob;
/**
 * Author：Vv on 2015/7/20 20:51
 * Mail：envyfan@qq.com
 * Description：
 */
public class AppContext extends BaseApplication {

    private static AppContext instance;

    private static final String ApplicationID = "这里输入你的Bmob ApplicationID";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(this, ApplicationID);
        //初始化Log系统
        Logger.init("MyDemo")               // default PRETTYLOGGER or use just init()
              .setMethodCount(1)            // default 2
              .hideThreadInfo();           // default shown
        //异常捕获收集
//        CrashWoodpecker.fly().to(this);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }


}
