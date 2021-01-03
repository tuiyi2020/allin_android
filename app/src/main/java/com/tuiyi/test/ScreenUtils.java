package com.tuiyi.test;

import android.content.Context;
import android.view.View;

import java.math.BigDecimal;

public class ScreenUtils {
    public static final double SPLASH_MIN = 0.49d;
    public static final double SPLASH_MIDDLE = 0.61d;
    public static final double SPLASH_MAX = 0.75d;

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    public static int getScreenWidthDip(Context context) {
        return px2dip(context, ScreenUtils.getScreenWidth(context));
    }

    public static int getScreenHeightDip(Context context) {
        return px2dip(context, ScreenUtils.getScreenHeight(context));
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static double getAspectRatio(View view) {
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return div(width, height);
    }

    public static double getScreenAspectRatio(Context context) {
        int height = getScreenHeight(context);
        int width = getScreenWidth(context);
        return div(width, height);
    }

    public static double div(int width, int height) {
        if (width == 0 || height == 0) {
            return 0d;
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(height));
        BigDecimal b2 = new BigDecimal(String.valueOf(width));
        return b2.divide(b1, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double div(float width, float height) {
        if (width == 0 || height == 0) {
            return 0d;
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(height));
        BigDecimal b2 = new BigDecimal(String.valueOf(width));
        return b2.divide(b1, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}