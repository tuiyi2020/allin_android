package com.tuiyi.allin.core.entity;

/**
 * @author liuhuijie
 * @date 1/9/21
 */
public class AdLocalEntity {

    public String unitid;//唯一id 来源id&来源位置id
    // public String sourceid;//来源id
    // public String placeid;//来源位置id
    public int daymax; //每日展示次数 小于等于0不限制
    public int hourmax;//每小时展示次数 小于等于0不限制
    // public int showinterval;//展示间隔 小于等于0不限制
    public long currentTime;//当前展示时间

    /*public AdLocalEntity getAdLocalEntityByJson(String json) {
        AdLocalEntity adLocalEntity = new AdLocalEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            adLocalEntity.placeid = jsonObject.getString("placeid");
            adLocalEntity.sourceid = jsonObject.getString("sourceid");
            adLocalEntity.daymax = jsonObject.getInt("daymax");
            adLocalEntity.hourmax = jsonObject.getInt("hourmax");
            adLocalEntity.showinterval = jsonObject.getInt("showinterval");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adLocalEntity;
    }

    public static String getAdLocalEntityJson(AdLocalEntity adEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("placeid", adEntity.placeid);
            jsonObject.put("sourceid", adEntity.sourceid);
            jsonObject.put("daymax", adEntity.daymax);
            jsonObject.put("hourmax", adEntity.hourmax);
            jsonObject.put("showinterval", adEntity.showinterval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }*/


}
