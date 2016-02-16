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



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(this, "这里填入你的Bmob Application ID");
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
