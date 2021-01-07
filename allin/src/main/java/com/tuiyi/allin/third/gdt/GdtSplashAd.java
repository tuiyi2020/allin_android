package com.tuiyi.allin.third.gdt;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

/**
 * 广点通 闪屏
 *
 * @author liuhuijie
 * @date 12/6/20
 */
public class GdtSplashAd extends CustomSplashAd {
    private SplashAD mSplashAD;
    private SplashADListener mSplashADListener;

    public GdtSplashAd() {

    }

    @Override
    public void customAdMessage(AdEntity adEntity) {


    }

    @Override
    public void loadAd() {
        mAdConfig.thirdPid = "8863364436303842593";
        mSplashADListener = new SplashADListener() {
            @Override
            public void onADClicked() {
                notifyAdClick();
            }

            @Override
            public void onADDismissed() {
                notifyAdClose();
            }

            @Override
            public void onADExposure() {
                notifyAdShow();
            }

            @Override
            public void onADLoaded(long l) {

            }

            @Override
            public void onADPresent() {

            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onNoAD(AdError adError) {
                notifyAdFail(new com.tuiyi.allin.core.AdError(adError.getErrorCode(), adError.getErrorMsg()));
            }
        };
        //6030789902606508
        mSplashAD = new SplashAD(mActivity, mViewContainer, mAdConfig.thirdPid, mSplashADListener, 5000);
        notifyAdReady();
    }

    @Override
    public void showAd() {
        if (mSplashAD != null) {
            mSplashAD.fetchAndShowIn(mViewContainer);
        }
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


}
