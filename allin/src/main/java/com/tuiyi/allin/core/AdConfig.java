package com.tuiyi.allin.core;


/**
 * @author liuhuijie
 * @date 2020/11/30
 */
public class AdConfig {
    public String placeId;
    public String appId; //三方appId
    public String thirdPid;//三方placeId
    public int width;
    public int height;
    public int showTime;
    //客户自定义 广告路径
    public String className;

    //推易
    public String json;//json不为空 为推易专属


    public AdConfig(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.showTime = builder.showTime;
        this.placeId = builder.placeId;
        this.thirdPid = builder.thirdPid;
        this.appId = builder.appId;
        this.className = builder.className;
        this.json = builder.json;
    }

    public static final class Builder {
        private int width;
        private int height;
        private int showTime;
        private String placeId;
        private String appId;
        private String thirdPid;
        private String className;
        private String json;

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

        public Builder setThirdAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setJson(String json) {
            this.json = json;
            return this;
        }


        public AdConfig build() {
            return new AdConfig(this);
        }
    }

}
