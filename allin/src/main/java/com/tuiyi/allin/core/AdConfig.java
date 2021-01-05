package com.tuiyi.allin.core;

import com.tuiyi.allin.core.entity.AdSourceEntity;

/**
 * @author liuhuijie
 * @date 2020/11/30
 */
public class AdConfig {
    public String placeId;
    public String thirdPid;//三方的placeId
    public int width;
    public int height;
    public int showTime;
    //客户自定义 广告路径
    public String className;

    //广告信息
    public String bid;
    public AdSourceEntity adSourceEntity;


    public AdConfig(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.showTime = builder.showTime;
        this.placeId = builder.placeId;
        this.thirdPid = builder.thirdPid;
    }

    public static final class Builder {
        private int width;
        private int height;
        private int showTime;
        private String placeId;
        private String thirdPid;
        private String className;
        //广告信息
        public String bid;
        public AdSourceEntity adSourceEntity;

        public Builder() {

        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setShowTime(int showTime) {
            this.showTime = showTime;
            return this;
        }

        public Builder setPlaceId(String placeId) {
            this.placeId = placeId;
            return this;
        }

        public Builder setThirdPid(String thirdPid) {
            this.thirdPid = thirdPid;
            return this;
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setBid(String bid) {
            this.bid = bid;
            return this;
        }

        public Builder setAdSource(AdSourceEntity adSourceEntity) {
            this.adSourceEntity = adSourceEntity;
            return this;
        }


        public AdConfig build() {
            return new AdConfig(this);
        }
    }

}
