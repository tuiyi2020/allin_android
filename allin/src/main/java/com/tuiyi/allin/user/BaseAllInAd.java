package com.tuiyi.allin.user;

import android.text.TextUtils;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.entity.AdSourceEntity;
import com.tuiyi.allin.core.net.NetApi;
import com.tuiyi.allin.core.net.NetListener;
import com.tuiyi.allin.core.net.NetManager;
import com.tuiyi.allin.utlis.AllInLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * AllIn广告基类
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public abstract class BaseAllInAd implements IAllInAd {

    /**
     * 获取广告类型
     *
     * @param adSourceEntity 内容
     */

    protected int getAdType(AdSourceEntity adSourceEntity) {
        if (!TextUtils.isEmpty(adSourceEntity.json)) {
            return AdFactory.TYPE_JSDK;
        }
        switch (adSourceEntity.sourceid) {
            case AdConstants.TYPE_JD:
                return AdFactory.TYPE_JD;
            case AdConstants.TYPE_GDT:
                return AdFactory.TYPE_GDT;
            case AdConstants.TYPE_TT:
                return AdFactory.TYPE_TT;
            case AdConstants.TYPE_CUSTOM://test
                return AdFactory.TYPE_CUSTOM;
            case AdConstants.TYPE_QY:
                return AdFactory.TYPE_QY;
            //custom
        }
        return -1;
    }

    /**
     * 网络获取广告信息
     */
    protected void makeRequest(String placeId, int width, int height, JSONObject deviceInfo, AdNetCallBack adNetCallBack) {
        //获取广告实体
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeid", placeId);
            jsonObject.put("adwidth", width);
            jsonObject.put("adheight", height);
            jsonObject.put("deviceinfo", deviceInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        AllInLog.i(json);
        new NetManager(new NetListener() {
            @Override
            public void onSuccess(String result) {
                AllInLog.i("success:" + result);
                if (adNetCallBack != null) {
                    AdEntity adEntity = new AdEntity().getAdEntityByResult(result);
                    if (adEntity.adsource == null || adEntity.adsource.isEmpty()) {
                        adNetCallBack.loadFail(new AdError(AdErrorCode.NO_AD_ERROR, "NO Ad"));
                    } else {
                        adNetCallBack.loadSuccess(adEntity);
                    }
                }
            }

            @Override
            public void onFail(String message) {
                AllInLog.e("fail:" + message);
                if (adNetCallBack != null) {
                    adNetCallBack.loadFail(new AdError(AdErrorCode.NET_ERROR, message));
                }
            }
        }).doPost(NetApi.GET_CONFIG, json);
    }

}
