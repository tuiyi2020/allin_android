package com.tuiyi.allin.core;

import android.app.Activity;

import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.entity.AdSourceEntity;
import com.tuiyi.allin.core.net.NetApi;
import com.tuiyi.allin.core.net.NetManager;
import com.tuiyi.allin.utlis.AllInLog;
import com.tuiyi.allin.utlis.SysInfoUtils;

import org.json.JSONObject;

/**
 * @author liuhuijie
 * @date 2020/11/15
 */
public abstract class AbstractAd implements IAd {

    protected Activity mActivity;

    protected AdConfig mAdConfig;

    protected AdCallback mAdCallback;

    public abstract void customAdMessage(AdEntity adEntity);

    public void setAdConfig(AdConfig adConfig) {
        this.mAdConfig = adConfig;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void setAdCallback(AdCallback adCallback) {
        this.mAdCallback = adCallback;
    }

    public void removeAdCallBack() {
        this.mAdCallback = null;
    }

    public final void notifyAdClick() {
        if (mAdCallback != null) {
            netLog(NetApi.CLICK_LOG);
            mAdCallback.onAdClick();
        }
    }

    public final void notifyAdReady() {
        if (mAdCallback != null) {
            mAdCallback.onAdReady();
        }
    }

    public final void notifyAdClose() {
        if (mAdCallback != null) {
            mAdCallback.onAdClose();
        }
    }

    public final void notifyAdFail(AdError adError) {
        if (mAdCallback != null) {
            mAdCallback.onAdFailed(adError);
        }
    }

    public final void notifyAdShow() {
        if (mAdCallback != null) {
            netLog(NetApi.SHOW_LOG);
            mAdCallback.onAdShow();
        }
    }
    /**
     * 广告日志
     *
     * @param url 请求地址
     */
    protected void netLog(String url) {
        if (mActivity!=null&&mAdConfig!=null){
            return;
        }
        //获取日志实体
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeid", mAdConfig.placeId);
            jsonObject.put("bid", mAdConfig.bid);
            jsonObject.put("deviceinfo", SysInfoUtils.getDeviceInfo(mActivity));

            if (mAdConfig.adSourceEntity!=null){
                JSONObject sourceObj = new JSONObject();
                AdSourceEntity adSourceEntity=mAdConfig.adSourceEntity;
                sourceObj.put("sourceid", adSourceEntity.sourceid);
                sourceObj.put("appid", adSourceEntity.appid);
                sourceObj.put("sourcePlaceid", adSourceEntity.placeid);
                sourceObj.put("type", adSourceEntity.type);
                jsonObject.put("source",sourceObj);
            }
        } catch (Exception e) {
            AllInLog.i(e.getMessage());
            e.printStackTrace();
        }
        new NetManager().doPost(url, jsonObject.toString());

    }
}
