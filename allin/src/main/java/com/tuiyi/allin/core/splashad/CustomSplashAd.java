package com.tuiyi.allin.core.splashad;

import android.view.ViewGroup;

import com.tuiyi.allin.core.AbstractAd;

/**
 * 闪屏广告
 *
 * @author liuhuijie
 * @date 2020/11/15
 */
public abstract class CustomSplashAd extends AbstractAd {

    protected ViewGroup mViewContainer;

    public void setViewContainer(ViewGroup mViewContainer) {
        this.mViewContainer = mViewContainer;
    }
}
