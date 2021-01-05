package com.tuiyi.allin.core.entity;

import android.text.TextUtils;

import com.tuiyi.allin.utlis.AllInLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告信息
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class AdEntity {

    public String msg;//非空表示正常，有内容标识错误信息
    public String bid;//请求id
    public List<AdSourceEntity> sourceid;//

    public AdEntity getAdEntityByResult(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        AdEntity entity = new AdEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.msg = jsonObject.getString("msg");
            entity.bid = jsonObject.getString("bid");
            try {
                JSONArray sourceIdJsonArray = jsonObject.getJSONArray("adsource");
                List<AdSourceEntity> adSourceEntityList=new ArrayList<>();
                for (int i = 0; i < sourceIdJsonArray.length(); i++) {
                    AdSourceEntity adSourceEntity = new AdSourceEntity();
                    JSONObject adsJsonObject = sourceIdJsonArray.getJSONObject(i);
                    adSourceEntity.sourceid = adsJsonObject.getString("sourceid");
                    adSourceEntity.appid = adsJsonObject.getString("appid");
                    adSourceEntity.placeid = adsJsonObject.getString("placeid");
                    adSourceEntity.daymax = adsJsonObject.getInt("daymax");
                    adSourceEntity.hourmax = adsJsonObject.getInt("hourmax");
                    try {
                        adSourceEntity.json = adsJsonObject.getString("json");
                    } catch (JSONException e) {
                    }
                    adSourceEntity.showinterval = adsJsonObject.getInt("showinterval");
                    try {
                        adSourceEntity.type=adsJsonObject.getString("type");
                    } catch (JSONException e) {
                    }
                    try {
                        adSourceEntity.classname=adsJsonObject.getString("classname");
                    } catch (JSONException e) {
                    }

                    adSourceEntityList.add(adSourceEntity);
                }
                entity.sourceid=adSourceEntityList;
            } catch (JSONException e) {

            }

        } catch (JSONException e) {
            AllInLog.e("JsonParseError" + e.getMessage());
            msg = e.getMessage();
            e.printStackTrace();
        }
        return entity;
    }

}
