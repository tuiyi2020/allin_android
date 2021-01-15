package com.tuiyi.allin.third.jsdk;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.imeiadx.jsdk.jy.mob.JyAd;
import cn.imeiadx.jsdk.jy.mob.JyAdListener2;
import cn.imeiadx.jsdk.jy.mob.JyNative;
import cn.imeiadx.jsdk.jy.mob.JyNormalAd;

/**
 * 推易轮播
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class JsdkBannerAd extends CustomBannerAd {
    private JyAdListener2 mJyAdListener2;
    private JyNormalAd mJyNormalAd;

    public JsdkBannerAd() {

    }

    @Override
    public void loadAd() {
        if (mAdConfig.json == null) {
            notifyAdFail(new AdError(AdErrorCode.NO_AD_ERROR, "无广告"));
            return;
        }
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
        mJyNormalAd = JyAd.getNormalAd(mActivity, mAdConfig.thirdPid, mAdConfig.width, mAdConfig.height, mViewContainer, mJyAdListener2);
        //mJySplashAd = new JySplashAd(mActivity, mAdConfig.thirdPid, -1, -1, mAdConfig.showTime, mJyAdListener2);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mAdConfig.json);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyAdFail(new AdError(AdErrorCode.PARSE_ERROR, "解析失败"));
            return;
        }
        mJyNormalAd.loadAd(jsonObject);

    }

    @Override
    public void showAd() {
        if (mJyNormalAd == null || !mJyNormalAd.hasAd()) {
            return;
        }
        mViewContainer.removeAllViews();
        mJyNormalAd.showAd();
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
