package com.tuiyi.allin.utlis;

import android.util.Log;

/**
 * 日志输出
 *
 * @author liuhuijie
 * @date 12/6/20
 */
public class AllInLog {

    private static final String TAG = "all_in";

    private static boolean logEnable = false;

    public static boolean getLogEnable(){
        return logEnable;
    }

    public static void enableLog(boolean logEnable) {
        AllInLog.logEnable = logEnable;
    }

    public static void e(String message) {
        if (!logEnable) {
            return;
        }
        Log.e(TAG, message);
    }

    public static void i(String message) {
        if (!logEnable) {
            return;
        }
        Log.i(TAG, message);
    }

    public static void d(String message) {
        if (!logEnable) {
            return;
        }
        Log.d(TAG, message);
    }
}
