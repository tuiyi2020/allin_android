package com.tuiyi.allin.third.gdt;

import android.app.Application;

import com.qq.e.comm.managers.GDTADManager;

/**
 * 可以用一个单例来保存GdtAdManager实例，在需要初始化sdk的时候调用
 */
public class GdtAdManagerHolder {

    private static boolean sInit;

    public static void init(Application application, String appId) {
        doInit(application, appId);
    }

    private static void doInit(Application application, String appId) {
        if (!sInit) {
            GDTADManager.getInstance().initWith(application, appId);
            sInit = true;
        }
    }


}
