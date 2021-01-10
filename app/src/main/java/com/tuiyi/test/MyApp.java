package com.tuiyi.test;

import android.app.Application;

import com.tuiyi.allin.utlis.AllInLog;

import cn.imeiadx.jsdk.util.OaidManager;

/**
 * @author liuhuijie
 * @date 2020/11/17
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AllInLog.enableLog(true);
        OaidManager.getOaid(this);
    }
}
