package com.tuiyi.allin.third.jd;

import android.view.View;

import com.jd.ad.sdk.imp.JadListener;
import com.jd.ad.sdk.imp.feed.FeedAd;
import com.jd.ad.sdk.work.JadPlacementParams;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.nativead.CustomNativeAd;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * Jd原生广告
 *
 * @author liuhuijie
 * @date 12/20/20
 */
public class JdNativeAd extends CustomNativeAd {

    private FeedAd feedAd;

    private boolean isRender;


    public JdNativeAd() {

    }

    @Override
    public void loadAd() {
        mViewContainer.removeAllViews();
        mAdConfig.thirdPid="6676";
        JdAdManagerHolder.init(mActivity.getApplication(), mAdConfig.appId);
        JadPlacementParams jadSlot = new JadPlacementParams.Builder()
                .setPlacementId(mAdConfig.thirdPid)
                .setSize(mAdConfig.width, mAdConfig.height)
                .setSupportDeepLink(false)
                .setCloseHide(false)
                .build();
        feedAd = new FeedAd(mActivity, jadSlot, new JadListener() {

            @Override
            public void onAdLoadSuccess() {
                AllInLog.i("FeedAd Load Success");

            }

            @Override
            public void onAdLoadFailed(int code, String error) {
                AllInLog.i("FeedAd Load Failed " + code + error);
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdRenderSuccess(View view) {
                AllInLog.i("FeedAd Render Success");
                isRender = true;
                feedAd.showAd(mViewContainer);
                notifyAdReady();
            }

            @Override
            public void onAdRenderFailed(int code, String error) {
                AllInLog.i("FeedAd Render Failed");
                notifyAdFail(new AdError(code, error));
            }

            @Override
            public void onAdClicked() {
                AllInLog.i("FeedAd Clicked");
                notifyAdClick();
            }

            @Override
            public void onAdExposure() {
                AllInLog.i("FeedAd Exposure Success");
                notifyAdShow();
            }

            @Override
            public void onAdDismissed() {
                AllInLog.i("FeedAd Dismissed");
                notifyAdClose();
            }
        });
        feedAd.loadAd();
    }

    @Override
    public void showAd() {
        if (feedAd == null) {
            return;
        }
        if (isRender) {

        }
    }

    @Override
    public void destroyAd() {
        if (feedAd != null) {
            feedAd.destroy();
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
