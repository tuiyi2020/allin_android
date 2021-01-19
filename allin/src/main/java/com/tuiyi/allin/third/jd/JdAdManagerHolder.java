package com.tuiyi.allin.third.jd;

import android.app.Application;

import com.jd.ad.sdk.JadYunSdk;
import com.jd.ad.sdk.JadYunSdkConfig;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * 可以用一个单例来保存JdAdManager实例，在需要初始化sdk的时候调用
 */
public class JdAdManagerHolder {

    private static boolean sInit;

    public static void init(Application application, String appId) {
        doInit(application, appId);
    }

    private static void doInit(Application application, String appId) {
        if (!sInit) {
            JadYunSdk.init(application, buildConfig(appId));
            sInit = true;
        }
    }

    private static JadYunSdkConfig buildConfig(String appId) {
        return new JadYunSdkConfig.Builder()
                .setAppId(appId)
                .setEnableLog(AllInLog.getLogEnable())
                .build();
    }
}
