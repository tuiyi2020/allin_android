package com.tuiyi.allin.core;

/**
 * sdk配置
 * @author liuhuijie
 * @date 1/28/21
 */
public class AllInSdkConfig {

    private boolean enableLog;

    private AllInSdkConfig(AllInSdkConfig.Builder builder) {
        this.enableLog = builder.enableLog;
    }

    public static AllInSdkConfig.Builder newConfig() {
        return new AllInSdkConfig.Builder();
    }

    public boolean isEnableLog() {
        return this.enableLog;
    }


    public static final class Builder {
        private boolean enableLog;

        private Builder() {
        }

        public AllInSdkConfig build() {
            return new AllInSdkConfig(this);
        }

        public AllInSdkConfig.Builder setEnableLog(boolean enableLog) {
            this.enableLog = enableLog;
            return this;
        }
    }
}
