package com.tuiyi.test;

import android.app.Application;

import com.tuiyi.allin.core.AllInSdk;
import com.tuiyi.allin.core.AllInSdkConfig;

/**
 * @author liuhuijie
 * @date 2020/11/17
 */
public class AllInApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //sdk配置
        AllInSdkConfig config = AllInSdkConfig.newConfig()
                //是否打开日志
                .setEnableLog(false)
                .build();
        //初始化sdk
        AllInSdk.init(this, config);
        PreferencesUtils.init(this);
    }
}
