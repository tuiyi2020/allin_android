package com.tuiyi.allin.third.jsdk;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

import cn.imeiadx.jsdk.jy.mob.JyAdListener2;
import cn.imeiadx.jsdk.jy.mob.JyNative;
import cn.imeiadx.jsdk.jy.mob.JySplashAd;

/**
 * 推易闪屏
 *
 * @author liuhuijie
 * @date 2020/11/16
 */
public class JsdkSplashAd extends CustomSplashAd {

    private JySplashAd mJySplashAd;
    private JyAdListener2 mJyAdListener2;


    public JsdkSplashAd() {

    }

    @Override
    public void loadAd() {
        mAdConfig.thirdPid="GL2TTLZJK3JTFWXECFJ1";
        mAdConfig.showTime=15;
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
                notifyAdClose();
            }

            @Override
            public void onADReceive() {
                super.onADReceive();
                notifyAdReady();
            }
        };
        mJySplashAd = new JySplashAd(mActivity, mAdConfig.thirdPid, mAdConfig.width, mAdConfig.height, mAdConfig.showTime, mJyAdListener2);
        mJySplashAd.loadAd();
    }

    @Override
    public void showAd() {
        if (mJySplashAd == null && mJySplashAd.hasAd()) {
            return;
        }
        mJySplashAd.showAd();
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
