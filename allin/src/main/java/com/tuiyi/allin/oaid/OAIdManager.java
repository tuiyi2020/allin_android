package com.tuiyi.allin.oaid;

import android.content.Context;
import android.text.TextUtils;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;
import com.tuiyi.allin.utlis.AllInLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class OAIdManager {

    private static String mOAId;

    public static String getOAId(Context context) {
        if (!TextUtils.isEmpty(mOAId)) {
            return mOAId;
        }
        try {
            int errorCode = MdidSdkHelper.InitSdk(context, true, new IIdentifierListener() {
                @Override
                public void OnSupport(boolean b, IdSupplier idSupplier) {
                    if (idSupplier == null) {
                        return;
                    }
                    mOAId = idSupplier.getOAID();
                }
            });
            if (errorCode == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT) {
                AllInLog.e("获取OAID：不支持的设备");
            } else if (errorCode == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE) {
                AllInLog.e("获取OAID：加载配置文件出错");
            } else if (errorCode == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {
                AllInLog.e("获取OAID：不支持的设备厂商");
            } else if (errorCode == ErrorCode.INIT_ERROR_RESULT_DELAY) {
                AllInLog.e("获取OAID：获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程");
            } else if (errorCode == ErrorCode.INIT_HELPER_CALL_ERROR) {
                AllInLog.e("获取OAID：反射调用出错");
            }
        } catch (Throwable e) {
            return getOAId2(context);
        }

        if (mOAId == null) {
            mOAId = "";
        }

        return mOAId;
    }

    public static class MyHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (args[1] == null) {
                return null;
            }
            mOAId = (String) args[1].getClass().getMethod("getOAID").invoke(args[1]);
            return null;
        }
    }

    //1.0.13
    private static String getOAId2(Context context) {
        if (!TextUtils.isEmpty(mOAId)) {
            return mOAId;
        }
        try {
            Class<?> callbackClazz = Class.forName("com.bun.supplier.IIdentifierListener");
            MyHandler myHandler = new MyHandler();
            Object myCallback = Proxy.newProxyInstance(
                    OAIdManager.class.getClassLoader(),
                    new Class[]{callbackClazz},
                    myHandler);
            Class<?> jLibraryClazz = Class.forName("com.bun.miitmdid.core.JLibrary");
            jLibraryClazz.getMethod("InitEntry", Context.class).invoke(null, context);

            Class<?> mdidSdkHelperClazz = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            int errorCode = (int) mdidSdkHelperClazz.getMethod("InitSdk", Context.class, boolean.class, callbackClazz).invoke(null, context, true, myCallback);
        } catch (Throwable e) {
            return getOAId3(context);
        }

        if (mOAId == null) {
            mOAId = "";
        }

        return mOAId;
    }

    //1.0.10
    private static String getOAId3(Context context) {
        if (!TextUtils.isEmpty(mOAId)) {
            return mOAId;
        }
        try {
            Class<?> callbackClazz = Class.forName("com.bun.miitmdid.core.IIdentifierListener");
            MyHandler myHandler = new MyHandler();
            Object myCallback = Proxy.newProxyInstance(
                    OAIdManager.class.getClassLoader(),
                    new Class[]{callbackClazz},
                    myHandler);
            Class<?> jLibraryClazz = Class.forName("com.bun.miitmdid.core.JLibrary");
            jLibraryClazz.getMethod("InitEntry", Context.class).invoke(null, context);

            Class<?> mdidSdkHelperClazz = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            int errorCode = (int) mdidSdkHelperClazz.getMethod("InitSdk", Context.class, boolean.class, callbackClazz).invoke(null, context, true, myCallback);
        } catch (Throwable e) {
            AllInLog.e("oaid Error"+e.getMessage());
        }

        if (mOAId == null) {
            mOAId = "";
        }

        return mOAId;
    }
}
