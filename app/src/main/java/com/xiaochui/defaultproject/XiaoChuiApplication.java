package com.xiaochui.defaultproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author Death丶Love
 * @project_name ZJBuildingEducation
 * @package_name com.xiaochui.zjbuildingeducation
 * @time 2018/7/13 14:38
 * @remark
 */
public class XiaoChuiApplication extends Application {
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initInstance();
    }

    private void initInstance() {
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }

    /**
     * 解决java 65536 方法数问题
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
