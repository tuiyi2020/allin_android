package com.tuiyi.allin.user;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
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
                return AdFactory.TYPE_JSDK;
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
        getConfig(json, adNetCallBack);
    }

    /**
     * 获取广告配置
     *
     * @param json 配置参数
     */
    private void getConfig(String json, AdNetCallBack adNetCallBack) {

        new NetManager(new NetListener() {
            @Override
            public void onSuccess(String result) {
                AllInLog.i("success:" + result);
                if (adNetCallBack != null) {
                    AdEntity adEntity = new AdEntity().getAdEntityByResult(result);
                    if (adEntity.sourceid == null || adEntity.sourceid.isEmpty()) {
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

    /**
     * 广告日志
     *
     * @param url  请求地址
     * @param json 日志信息
     */
    protected void netLog(String url, String json) {

        new NetManager().doPost(url, json);

    }


}
