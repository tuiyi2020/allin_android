package com.tuiyi.allin.third.jsdk;

import android.widget.FrameLayout;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;

import cn.imeiadx.jsdk.jy.mob.JyAd;
import cn.imeiadx.jsdk.jy.mob.JyAdListener2;
import cn.imeiadx.jsdk.jy.mob.JyAdView;
import cn.imeiadx.jsdk.jy.mob.JyNative;

/**
 * 推易轮播
 * @author liuhuijie
 * @date 2020/11/20
 */
public class JsdkBannerAd extends CustomBannerAd {
    private JyAdListener2 mJyAdListener2;
    private JyAdView mJyAdView;
    public JsdkBannerAd(){

    }
    @Override
    public void loadAd() {
        mAdConfig.thirdPid = "GL2TTLZJK3JTFWXECFJ1";
        mAdConfig.width = -1;
        mAdConfig.height = -1;

        mJyAdListener2 = new JyAdListener2() {
            @Override
            public void onADClicked() {
                super.onADClicked();
                notifyAdClick();
            }

            @Override
            public void onADExposure() {
                super.onADExposure();
                // notifyAdReady();
            }

            @Override
            public void onADReceive(JyNative jyNative) {
                super.onADReceive(jyNative);
                //notifyAdReady();
            }

            @Override
            public void onNoAD(String err) {
                super.onNoAD(err);
                notifyAdFail(new AdError(0, err));
            }

            @Override
            public void onClosed() {
                super.onClosed();
                if (mJyAdView != null) {
                    // val viewGroupParent = findViewById<ViewGroup>(android.R.id.content)
                    if (mViewContainer != null && mViewContainer.getChildCount() > 0) {
                        mViewContainer.removeView(mJyAdView);
                    }
                }
                notifyAdClose();
            }

            @Override
            public void onADReceive() {
                super.onADReceive();
                notifyAdReady();
            }
        };

        mJyAdView = JyAd.initNormalAdView(mActivity, mAdConfig.thirdPid, mAdConfig.width,
                mAdConfig.height, mJyAdListener2, true);
        mJyAdView.setOpen(false);
        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
//        mNormalAd.setOpen(false);
        mViewContainer.removeAllViews();
        mViewContainer.addView(mJyAdView, params);

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
