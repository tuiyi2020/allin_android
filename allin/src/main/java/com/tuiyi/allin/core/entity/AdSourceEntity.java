package com.tuiyi.allin.core.entity;

/**
 * 返回来源内容
 *
 * @author liuhuijie
 * @date 12/25/20
 */
public class AdSourceEntity {

    public String sourceid;
    public String appid;
    public String placeid;
    public int hourmax;//小于等于0表示不限制(展示次数)
    public int daymax;//小于等于0表示不限制(展示次数)
    public int showinterval;//小于等于0表示不限制(秒)
    public String type;//来源类型
    public String classname;//来源类名
    public String json;//广告内容，只有类型是推易时有

}


