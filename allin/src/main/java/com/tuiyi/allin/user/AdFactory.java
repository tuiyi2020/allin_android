package com.tuiyi.allin.user;


import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.insertad.CustomInsertAd;
import com.tuiyi.allin.core.nativead.CustomNativeAd;
import com.tuiyi.allin.core.splashad.CustomSplashAd;
import com.tuiyi.allin.third.gdt.GdtBannerAd;
import com.tuiyi.allin.third.gdt.GdtInsertAd;
import com.tuiyi.allin.third.gdt.GdtNativeAd;
import com.tuiyi.allin.third.gdt.GdtSplashAd;
import com.tuiyi.allin.third.jd.JdBannerAd;
import com.tuiyi.allin.third.jd.JdInsertAd;
import com.tuiyi.allin.third.jd.JdNativeAd;
import com.tuiyi.allin.third.jd.JdSplashAd;
import com.tuiyi.allin.third.jsdk.JsdkBannerAd;
import com.tuiyi.allin.third.jsdk.JsdkInsertAd;
import com.tuiyi.allin.third.jsdk.JsdkNativeAd;
import com.tuiyi.allin.third.jsdk.JsdkSplashAd;
import com.tuiyi.allin.third.qy.QyBannerAd;
import com.tuiyi.allin.third.qy.QySplashAd;
import com.tuiyi.allin.third.tt.TTBannerAd;
import com.tuiyi.allin.third.tt.TTInsertAd;
import com.tuiyi.allin.third.tt.TTNativeAd;
import com.tuiyi.allin.third.tt.TTSplashAd;

/**
 * @author liuhuijie
 * @date 2020/11/16
 */
public class AdFactory {
    // 广点通SDK L1YPCMZ8TMW8NV6I2QXE
    // 穿山甲SDK LDPXSWDIYICUNBFU4EMM
    // 京东SDK URULEHLH21ZFVXESMGBH
    public static final int TYPE_JSDK = 0;
    public static final int TYPE_GDT = 1;
    public static final int TYPE_BAIDU = 2;
    public static final int TYPE_JD = 3;
    public static final int TYPE_TT = 4;
    public static final int TYPE_CUSTOM = 5;
    public static final int TYPE_QY = 6;

    public static CustomSplashAd getSplashAd(int type, AdConfig adConfig) {
        switch (type) {
            case TYPE_GDT:
                return new GdtSplashAd();
            case TYPE_JD:
                return new JdSplashAd();
            case TYPE_TT:
                return new TTSplashAd();
            case TYPE_JSDK:
                return new JsdkSplashAd();
            case TYPE_QY:
                return new QySplashAd();
            case TYPE_CUSTOM:
                String className = adConfig.className;
                Object object = getInstanceByClassName(className);
                return (CustomSplashAd) object;
            default:
                return null;
        }


    }

    public static CustomInsertAd getInsertAd(int type, AdConfig adConfig) {
        switch (type) {
            case TYPE_GDT:
                return new GdtInsertAd();
            case TYPE_JD:
                return new JdInsertAd();
            case TYPE_TT:
                return new TTInsertAd();
            case TYPE_JSDK:
                return new JsdkInsertAd();
            case TYPE_CUSTOM:
                String className = adConfig.className;
                Object object = getInstanceByClassName(className);
                return (CustomInsertAd) object;
            default:
                return null;
        }

    }

    public static CustomNativeAd getNativeAd(int type, AdConfig adConfig) {
        switch (type) {
            case TYPE_GDT:
                return new GdtNativeAd();
            case TYPE_JD:
                return new JdNativeAd();
            case TYPE_TT:
                return new TTNativeAd();
            case TYPE_JSDK:
                return new JsdkNativeAd();
            case TYPE_CUSTOM:
                String className = adConfig.className;
                Object object = getInstanceByClassName(className);
                return (CustomNativeAd) object;
            default:
                return null;
        }

    }

    public static CustomBannerAd getBannerAd(int type, AdConfig adConfig) {
        switch (type) {
            case TYPE_GDT:
                return new GdtBannerAd();
            case TYPE_JD:
                return new JdBannerAd();
            case TYPE_TT:
                return new TTBannerAd();
            case TYPE_JSDK:
                return new JsdkBannerAd();
            case TYPE_QY:
                return new QyBannerAd();
            case TYPE_CUSTOM:
                String className = adConfig.className;
                Object object = getInstanceByClassName(className);
                return (CustomBannerAd) object;
            default:
                return null;
        }

    }

    public static Object getInstanceByClassName(String className) {
        try {
            Class splashClass = Class.forName(className);
            return splashClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
