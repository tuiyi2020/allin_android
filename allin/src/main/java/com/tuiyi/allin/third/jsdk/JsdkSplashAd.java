package com.tuiyi.allin.third.jsdk;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

import org.json.JSONException;
import org.json.JSONObject;

import cn.imeiadx.jsdk.jy.mob.JyAd;
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
        if (mAdConfig.json == null) {
            notifyAdFail(new AdError(AdErrorCode.NO_AD_ERROR, "无广告"));
            return;
        }
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
                notifyAdShow();
            }

            @Override
            public void onADReceive(JyNative jyNative) {
                super.onADReceive(jyNative);

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
        mJySplashAd= JyAd.getSplashAd(mActivity,null,mAdConfig.width,mAdConfig.height,mAdConfig.showTime,mJyAdListener2);
        //mJySplashAd = new JySplashAd(mActivity, mAdConfig.thirdPid, -1, -1, mAdConfig.showTime, mJyAdListener2);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(mAdConfig.json);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyAdFail(new AdError(AdErrorCode.PARSE_ERROR, "解析失败"));
            return;
        }
        mJySplashAd.loadAd(jsonObject);
    }

    @Override
    public void showAd() {
        if (mJySplashAd == null ||!mJySplashAd.hasAd()) {
            return;
        }
        mJySplashAd.showAd();
    }

    @Override
    public void destroyAd() {
        // JyAd.exit();
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
