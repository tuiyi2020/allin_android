package com.tuiyi.allin.third.gdt;

import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.util.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.insertad.CustomInsertAd;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * 广点通 插屏
 *
 * @author liuhuijie
 * @date 12/21/20
 */
public class GdtInsertAd extends CustomInsertAd {

    private UnifiedInterstitialAD mInsertAd;

    public GdtInsertAd() {

    }

    @Override
    public void loadAd() {
        GdtAdManagerHolder.init(mActivity.getApplication(), mAdConfig.appId);
        mInsertAd = new UnifiedInterstitialAD(mActivity, mAdConfig.thirdPid, new UnifiedInterstitialADListener() {
            @Override
            public void onADClicked() {
                notifyAdClick();
            }

            @Override
            public void onADClosed() {
                notifyAdClose();
            }

            @Override
            public void onADExposure() {
                notifyAdShow();
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADReceive() {
                notifyAdReady();
            }

            @Override
            public void onNoAD(AdError adError) {
                AllInLog.i(adError.getErrorMsg() + "==" + adError.getErrorCode());
                notifyAdFail(new com.tuiyi.allin.core.AdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onVideoCached() {

            }
        });
        mInsertAd.loadAD();
    }

    @Override
    public void showAd() {
        if (mInsertAd != null) {
            mInsertAd.show();
        }
    }

    @Override
    public void destroyAd() {
        if (mInsertAd != null) {
            mInsertAd.destroy();
        }
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
