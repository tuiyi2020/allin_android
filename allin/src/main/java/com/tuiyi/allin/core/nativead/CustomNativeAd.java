package com.tuiyi.allin.core.nativead;

import android.view.ViewGroup;

import com.tuiyi.allin.core.AbstractAd;

/**
 * 本地广告
 * @author liuhuijie
 * @date 2020/11/15
 */
public abstract class CustomNativeAd extends AbstractAd {

    protected ViewGroup mViewContainer;

    public void setViewContainer(ViewGroup mViewContainer) {
        this.mViewContainer = mViewContainer;
    }

}
