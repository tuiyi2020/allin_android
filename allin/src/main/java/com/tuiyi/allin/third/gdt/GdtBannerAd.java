package com.tuiyi.allin.third.gdt;

import android.app.Activity;
import android.graphics.Point;
import android.widget.FrameLayout;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;

/**
 * 广点通 横幅广告
 *
 * @author liuhuijie
 * @date 12/21/20
 */
public class GdtBannerAd extends CustomBannerAd {

    private UnifiedBannerView mBannerView;

    public GdtBannerAd() {

    }

    private static FrameLayout.LayoutParams getUnifiedBannerLayoutParams(Activity activity) {
        Point screenSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        return new FrameLayout.LayoutParams(screenSize.x, Math.round(screenSize.x / 6.4F));
    }

    @Override
    public void loadAd() {

        mAdConfig.thirdPid = "4080052898050840";
        mBannerView = new UnifiedBannerView(mActivity, mAdConfig.thirdPid, new UnifiedBannerADListener() {
            @Override
            public void onADClicked() {
                notifyAdClick();
            }

            @Override
            public void onADCloseOverlay() {
                notifyAdClose();
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
            public void onADOpenOverlay() {

            }

            @Override
            public void onADReceive() {
            }

            @Override
            public void onNoAD(AdError adError) {
                notifyAdFail(new com.tuiyi.allin.core.AdError(adError.getErrorCode(), adError.getErrorMsg()));
            }
        });
        notifyAdReady();
    }

    @Override
    public void showAd() {
        mViewContainer.removeAllViews();
        mViewContainer
                .addView(mBannerView, getUnifiedBannerLayoutParams(mActivity));
        mBannerView.loadAD();
    }

    @Override
    public void destroyAd() {
        if (mBannerView != null) {
            mBannerView.destroy();
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
