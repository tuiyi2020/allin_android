package com.tuiyi.allin.core.bannerad;

import android.view.ViewGroup;

import com.tuiyi.allin.core.AbstractAd;

/**
 * 轮播图广告
 *
 * @author liuhuijie
 * @date 2020/11/15
 */
public abstract class CustomBannerAd extends AbstractAd {

    protected ViewGroup mViewContainer;

    public void setViewContainer(ViewGroup mViewContainer) {
        this.mViewContainer = mViewContainer;
    }
}
