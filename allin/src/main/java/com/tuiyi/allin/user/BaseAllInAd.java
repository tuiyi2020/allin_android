package com.tuiyi.allin.user;

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
     * @param type 后台类型
     */

    protected int getAdType(String type) {
        switch (type) {
            case AdConstants.TYPE_JD:
                return AdFactory.TYPE_JD;
            case AdConstants.TYPE_GDT:
                return AdFactory.TYPE_GDT;
            case AdConstants.TYPE_TT:
                return AdFactory.TYPE_TT;
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
                    if (adEntity.sourceid == null || adEntity.sourceid.isEmpty()) {
                        adNetCallBack.loadFail(new AdError(AdErrorCode.NO_AD_ERROR, "NO Ad"));
                        netLog(NetApi.REQUEST_LOG, placeId, adEntity.bid, deviceInfo, null, null, null, null, null);
                    } else {
                        AdSourceEntity sourceEntity = adEntity.sourceid.get(0);
                        netLog(NetApi.REQUEST_LOG, placeId, adEntity.bid, deviceInfo, sourceEntity.sourceid, sourceEntity.appid, sourceEntity.placeid, sourceEntity.type, null);
                        adNetCallBack.loadSuccess(adEntity);
                    }
                }
            }

            @Override
            public void onFail(String message) {
                AllInLog.e("fail:" + message);
                if (adNetCallBack != null) {
                    adNetCallBack.loadFail(new AdError(AdErrorCode.NET_ERROR, message));
                    netLog(NetApi.REQUEST_LOG, placeId, null, deviceInfo, null, null, null, null, null);
                }
            }
        }).doPost(NetApi.GET_CONFIG, json);

        //getConfig(json,deviceInfo, adNetCallBack);
    }

    /**
     * 广告日志
     *
     * @param url 请求地址
     */
    protected void netLog(String url, String placeid, String bid, JSONObject deviceInfo, String sourceid, String appid, String sourcePlaceid, String type, String error) {
        //获取日志实体
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeid", placeid);
            jsonObject.put("bid", bid);
            jsonObject.put("deviceinfo", deviceInfo);

            JSONObject sourceObj = new JSONObject();
            sourceObj.put("sourceid", sourceid);
            sourceObj.put("appid", appid);
            sourceObj.put("sourcePlaceid", sourcePlaceid);
            sourceObj.put("type", type);
            sourceObj.put("error", error);
            jsonObject.put("source",sourceObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
         new NetManager().doPost(url, jsonObject.toString());

    }


}
