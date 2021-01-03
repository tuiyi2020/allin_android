package com.tuiyi.test;

import android.app.Application;

import com.jd.ad.sdk.JadYunSdk;
import com.jd.ad.sdk.JadYunSdkConfig;
import com.qq.e.comm.managers.GDTADManager;
import com.tuiyi.allin.third.tt.TTAdManagerHolder;
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
        TTAdManagerHolder.init(this, "5129336");
        JadYunSdk.init(this,
                new JadYunSdkConfig.Builder()
                        .setAppId("337230")
                        //.setAppId("116567")
                        .setEnableLog(true)
                        .build());

        /**
         * 广点通临时使用demo id
         */
        // 通过调用此方法初始化 SDK。如果需要在多个进程拉取广告，每个进程都需要初始化 SDK。
        GDTADManager.getInstance().initWith(this, "1101152570");
        //GDTADManager.getInstance().initWith(this, "1109995936");
    }
}
