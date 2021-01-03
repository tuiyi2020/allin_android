package com.tuiyi.allin.third.jd;

import android.view.View;

import com.jd.ad.sdk.imp.JadListener;
import com.jd.ad.sdk.imp.banner.BannerAd;
import com.jd.ad.sdk.work.JadPlacementParams;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * 京东轮播图
 *
 * @author liuhuijie
 * @date 12/19/20
 */
public class JdBannerAd extends CustomBannerAd {

    private BannerAd bannerAd;

    private boolean isRender;

    public JdBannerAd() {

    }

    @Override
    public void loadAd() {
        if (mViewContainer != null) {
            mViewContainer.removeAllViews();
        }
        JadPlacementParams jadSlot = new JadPlacementParams.Builder()
                .setPlacementId(mAdConfig.thirdPid)
                .setSize(mAdConfig.width, mAdConfig.height)
                .setSupportDeepLink(true)
                .setCloseHide(true)
                .build();

        bannerAd = new BannerAd(mActivity, jadSlot, new JadListener() {
            @Override
            public void onAdLoadSuccess() {
                AllInLog.i("BannerAd Load Success");

            }

            @Override
            public void onAdLoadFailed(int code, String error) {
                AllInLog.i("BannerAd Load Failed " + error);
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdRenderSuccess(View view) {
                AllInLog.i("BannerAd Render Success");
                isRender = true;
                notifyAdReady();
            }

            @Override
            public void onAdRenderFailed(int code, String error) {
                AllInLog.i("BannerAd Render Failed");
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdClicked() {
                AllInLog.i("BannerAd Clicked");
                notifyAdClick();
            }

            @Override
            public void onAdExposure() {
                AllInLog.i("BannerAd Exposure Success");
                notifyAdShow();
            }

            @Override
            public void onAdDismissed() {
                AllInLog.i("BannerAd Dismissed");
                notifyAdClose();
            }
        });
        bannerAd.loadAd();

    }

    @Override
    public void showAd() {
        if (!isRender) {
            return;
        }
        if (bannerAd == null || mViewContainer == null) {
            return;
        }
        bannerAd.showAd(mViewContainer);
    }

    @Override
    public void destroyAd() {
        if (bannerAd != null) {
            bannerAd.destroy();
            bannerAd = null;
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
