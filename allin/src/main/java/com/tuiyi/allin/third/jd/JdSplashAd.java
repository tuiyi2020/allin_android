package com.tuiyi.allin.third.jd;

import android.view.View;

import com.jd.ad.sdk.imp.JadListener;
import com.jd.ad.sdk.imp.splash.SplashAd;
import com.jd.ad.sdk.work.JadPlacementParams;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

/**
 * 京东闪屏广告
 *
 * @author liuhuijie
 * @date 2020/12/10
 */
public class JdSplashAd extends CustomSplashAd {

     private SplashAd mSplashAd;

    public JdSplashAd() {

    }

    @Override
    public void loadAd() {
        JadPlacementParams jadParams = null;
        jadParams = new JadPlacementParams.Builder()
                .setPlacementId(mAdConfig.thirdPid)
                .setSize(mAdConfig.width, mAdConfig.height)
                .setSupportDeepLink(false)
                .setTimeout(5)
                .build();
        mSplashAd = new SplashAd(mActivity, jadParams, new JadListener() {
            @Override
            public void onAdLoadSuccess() {
            }

            @Override
            public void onAdLoadFailed(int i, String s) {

            }

            @Override
            public void onAdRenderSuccess(View view) {
                notifyAdReady();
                mSplashAd.showAd(mViewContainer);
            }

            @Override
            public void onAdRenderFailed(int i, String s) {

            }

            @Override
            public void onAdClicked() {
                notifyAdClick();
            }

            @Override
            public void onAdExposure() {
                notifyAdShow();
            }

            @Override
            public void onAdDismissed() {
                notifyAdClose();
            }
        });
        mSplashAd.loadAd();

    }

    @Override
    public void showAd() {

    }

    @Override
    public void destroyAd() {

    }

    @Override
    public void onPauseAd() {

    }

    @Override
    public void onResumeAd() {

    }

    @Override
    public void customAdMessage(AdEntity adEntity) {

    }
}
