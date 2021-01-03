package com.tuiyi.allin.core;

/**
 * @author liuhuijie
 * @date 2020/11/15
 */
public interface AdCallback {

    /**
     * 加载广告失败
     * @param error 错误信息
     */
    void onAdFailed(AdError error);

    /**
     * 点击广告
     */
    void onAdClick();

    /**
     * 广告加载成功
     */
    void onAdReady();

    /**
     * 广告展示
     */
    void onAdShow();

    /**
     * 广告关闭
     */
    void onAdClose();

}
