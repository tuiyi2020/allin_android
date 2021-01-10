package com.tuiyi.allin.utlis;

import android.content.Context;

import com.tuiyi.allin.core.database.AllInDatabase;
import com.tuiyi.allin.core.entity.AdSourceEntity;

/**
 * 本地缓存工具
 *
 * @author liuhuijie
 * @date 1/7/21
 */
public class LocalUtils {

    //24小时
    public final static long DAY_TIME = 24 * 60 * 60 * 1000l;

    //1个小时
    public final static long HOUR_TIME = 60 * 60 * 1000l;

    /**
     * 储存展示记录
     *
     * @param context
     * @param unitId
     */
    public static void insertAd(Context context, String unitId) {
        try {
            AllInDatabase allInDatabase = new AllInDatabase(context);
            long time = System.currentTimeMillis();
            allInDatabase.insert(unitId, time);
        } catch (Exception e) {
            AllInLog.e(e.getMessage());
        }

    }

    /**
     * 删除24小时之前的记录
     *
     * @param context
     */
    public static void deleteAd(Context context) {
        try {
            AllInDatabase allInDatabase = new AllInDatabase(context);
            long time = System.currentTimeMillis() - DAY_TIME;
            allInDatabase.delete(time);
        } catch (Exception e) {
            AllInLog.e(e.getMessage());
        }
    }

    /**
     * 获取特定时长广告展示次数
     *
     * @param unitId 广告唯一id appId+placeId
     * @param time   时长
     */
    public static int getAdCount(Context context, String unitId, long time) {
        int count = 0;
        try {
            AllInDatabase allInDatabase = new AllInDatabase(context);
            count = allInDatabase.getCountByTime(unitId, time);
        } catch (Exception e) {
            AllInLog.e(e.getMessage());
        }
        return count;
    }

    /**
     * 获取最近一次展示时间
     *
     * @param context
     * @param unitId
     */
    public static long getLastShowTime(Context context, String unitId) {
        long time = 0;
        try {
            AllInDatabase allInDatabase = new AllInDatabase(context);
            time = allInDatabase.getLastTime(unitId);
        } catch (Exception e) {
            AllInLog.e(e.getMessage());
        }
        return time;

    }

    /**
     * 判断广告资源是否可用
     *
     * @param context
     * @param adSourceEntity
     * @return
     */
    public static boolean getAdIsAvailable(Context context, AdSourceEntity adSourceEntity) {
        long lastShowTime = getLastShowTime(context, getUnitId(adSourceEntity));
        if (adSourceEntity.showinterval > 0 && lastShowTime > 0) {
            long passTime = System.currentTimeMillis() - lastShowTime;
            if (adSourceEntity.showinterval * 1000 > passTime) {
                //小于展示间隔
                return false;
            }
        }
        long currentTime = System.currentTimeMillis();
        if (adSourceEntity.hourmax > 0) {
            int hourLimitTimes = getAdCount(context, getUnitId(adSourceEntity), currentTime - HOUR_TIME);
            if (hourLimitTimes >= adSourceEntity.hourmax) {
                //单位小时已经展示最大次数
                return false;
            }
        }
        if (adSourceEntity.daymax <= 0) {
            int dayLimitTimes = getAdCount(context, getUnitId(adSourceEntity), currentTime - DAY_TIME);
            if (dayLimitTimes>=adSourceEntity.daymax) {
                //单位日已经展示最大次数
                return false;
            }
        }
        return true;
    }

    public static String getUnitId(AdSourceEntity adSourceEntity) {
        if (adSourceEntity == null) {
            return null;
        }
        return adSourceEntity.appid + adSourceEntity.placeid;
    }


}
