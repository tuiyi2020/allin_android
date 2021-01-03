package com.tuiyi.allin.third.jd;

import android.view.View;

import com.jd.ad.sdk.imp.JadListener;
import com.jd.ad.sdk.imp.interstitial.InterstitialAd;
import com.jd.ad.sdk.work.JadPlacementParams;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.insertad.CustomInsertAd;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * Jd插屏广告
 *
 * @author liuhuijie
 * @date 12/19/20
 */
public class JdInsertAd extends CustomInsertAd {

    private InterstitialAd interstitialAd;

    private boolean isRender;

    public JdInsertAd() {

    }

    @Override
    public void loadAd() {
        JadPlacementParams jadSlot = new JadPlacementParams.Builder()
                .setPlacementId(mAdConfig.thirdPid)
                .setSize(mAdConfig.width, mAdConfig.height)
                .setSupportDeepLink(false)
                .build();
        interstitialAd = new InterstitialAd(mActivity, jadSlot, new JadListener() {

            @Override
            public void onAdLoadSuccess() {
                AllInLog.i("InterstitialAd Load Success");
            }

            @Override
            public void onAdLoadFailed(int code, String error) {
                AllInLog.i("InterstitialAd Load onAdLoadFailed " + error);
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdRenderSuccess(View view) {
                AllInLog.i("InterstitialAd Render Success");
                isRender = true;


            }

            @Override
            public void onAdRenderFailed(int code, String error) {
                AllInLog.i("InterstitialAd Render Failed " + error);
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdClicked() {
                AllInLog.i("InterstitialAd Clicked");
                notifyAdClick();
            }

            @Override
            public void onAdExposure() {
                AllInLog.i("InterstitialAd  onAdExposure");
                notifyAdShow();
            }

            @Override
            public void onAdDismissed() {
                AllInLog.i("InterstitialAd Dismissed");
                notifyAdClose();
            }
        });
        interstitialAd.loadAd();

    }

    @Override
    public void showAd() {

        if (interstitialAd == null) {
            return;
        }
        if (isRender) {
            interstitialAd.showAd(null);
        }
    }

    @Override
    public void destroyAd() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
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
