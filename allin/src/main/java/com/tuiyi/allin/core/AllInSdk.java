package com.tuiyi.allin.core;

import android.content.Context;

import com.tuiyi.allin.utlis.AllInLog;

import cn.imeiadx.jsdk.util.OaidManager;

/**
 * sdk入口
 *
 * @author liuhuijie
 * @date 1/28/21
 */
public class AllInSdk {
    public static void init(Context context, AllInSdkConfig allInSdkConfig) {
        if (context != null) {
            OaidManager.getOaid(context);
        } else {
            throw new RuntimeException("context为空");
        }
        if (allInSdkConfig != null) {
            AllInLog.enableLog(allInSdkConfig.isEnableLog());
        }

    }
}
