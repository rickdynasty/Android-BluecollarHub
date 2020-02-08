package com.bluecollar.lib.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * @Author: rick_tan
 * @Date: 19-7-20
 * @Version: v1.0
 * @Des 应用程序在底层LibBase处的代理，方便上层调用应用程序的基础数据：1、application 2、isDbug 3、Context
 * 另外，在这里会对LibBase的基础库功能进行初始化
 * @modify On 2018-08-28 by author for reason ...
 */
public class AppProxy {
    private static final String TAG = AppProxy.class.getSimpleName();

    // 注意这里的值,和主工程的渠道配置是对应的
    private static Application sApplication;

    private String versionName;
    private boolean sIsDebug = false;

    public static AppProxy getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final AppProxy instance = new AppProxy();
    }

    public AppProxy init(Application application) {
        return init(application, false);
    }

    public AppProxy init(Application application, boolean productModel) {
        if (null == application) {
            throw new IllegalArgumentException("Illega application Exception, please check~ !");
        }

        AppProxy.sApplication = application;

        // 非productModel初始化 Stetho
        if (!productModel) {
            //^ 如果是生产环境，……
        }

        // init LeakCanary
        // ^ Write code here

        Log.d(TAG, "debug? => " + sIsDebug);

        return this;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public AppProxy setIsDebug(boolean isDebug) {
        sIsDebug = isDebug;
        return this;
    }

    public static Application getApplication() {
        if (null == sApplication) {
            throw new IllegalAccessError("Please initialize the AppProxy first.");
        }

        return sApplication;
    }

    public boolean isDebug() {
        return this.sIsDebug;
    }

    public static Context getContext() {
        if (null == sApplication) {
            throw new IllegalAccessError("Please initialize the AppProxy first.");
        }

        return sApplication.getApplicationContext();
    }
}
