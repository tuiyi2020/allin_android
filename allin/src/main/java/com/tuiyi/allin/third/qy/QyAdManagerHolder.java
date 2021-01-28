package com.tuiyi.allin.third.qy;

import android.content.Context;

import com.mcto.sspsdk.QyClient;
import com.mcto.sspsdk.QySdk;
import com.mcto.sspsdk.QySdkConfig;
import com.tuiyi.allin.utlis.SysInfoUtils;

/**
 * 爱奇艺sdk初始化管理
 * @author liuhuijie
 * @date 1/27/21
 */
public class QyAdManagerHolder {
    private static boolean sInit;

    public static QyClient get() {
        if (!sInit) {
            throw new RuntimeException("QySdk is not init, please check.");
        }
        return QySdk.getAdClient();
    }

    static void init(Context context,String appId) {
        if (!sInit) {
            QySdk.init(context, buildConfig(appId, SysInfoUtils.getAppName(context)));
            sInit = true;
        }
    }

    private static QySdkConfig buildConfig(String appId,String appName) {
        return QySdkConfig.newAdConfig()
                //测试用媒体id，接入方填从平台申请到的媒体id
                .appId(appId)
                .appName(appName)
                .debug(true)
                .build();
    }
}
