package com.tuiyi.allin.core;

import android.app.Activity;

import com.tuiyi.allin.core.entity.AdEntity;

/**
 * @author liuhuijie
 * @date 2020/11/15
 */
public abstract class AbstractAd implements IAd {

    protected Activity mActivity;

    protected AdConfig mAdConfig;

    protected AdCallback mAdCallback;

    public abstract void customAdMessage(AdEntity adEntity);

    public void setAdConfig(AdConfig adConfig) {
        this.mAdConfig = adConfig;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void setAdCallback(AdCallback adCallback) {
        this.mAdCallback = adCallback;
    }

    public void removeAdCallBack() {
        this.mAdCallback = null;
    }

    public final void notifyAdClick() {
        if (mAdCallback != null) {
            mAdCallback.onAdClick();
        }
    }

    public final void notifyAdReady() {
        if (mAdCallback != null) {
            mAdCallback.onAdReady();
        }
    }

    public final void notifyAdClose() {
        if (mAdCallback != null) {
            mAdCallback.onAdClose();
        }
    }

    public final void notifyAdFail(AdError adError) {
        if (mAdCallback != null) {
            mAdCallback.onAdFailed(adError);
        }
    }

    public final void notifyAdShow() {
        if (mAdCallback != null) {
            mAdCallback.onAdShow();
        }
    }

}
