package com.tuiyi.allin.core;


/**
 * @author liuhuijie
 * @date 2020/11/15
 */
public interface IAd {

    void loadAd();

    void showAd();

    void destroyAd();

    void onPauseAd();

    void onResumeAd();

}
