package com.tuiyi.allin.third.baidu;

import android.app.Activity;
import android.view.ViewGroup;

import com.baidu.mobads.AdView;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.insertad.CustomInsertAd;

/**
 * 百度插屏
 * @author liuhuijie
 * @date 12/21/20
 */
public class BdInsertAd extends CustomInsertAd {
    private Activity mActivity;
    private InterstitialAd mInsertAd;
    private boolean isReady;
    public BdInsertAd(Activity activity, ViewGroup viewGroup,String appId,String posId){
        mActivity=activity;
        AdView.setAppSid(activity, appId);
        // 创建插屏广告
        mInsertAd = new InterstitialAd(activity, posId);
        mInsertAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReady() {
                isReady=true;
                notifyAdReady();
            }

            @Override
            public void onAdPresent() {
                notifyAdShow();
            }

            @Override
            public void onAdClick(InterstitialAd interstitialAd) {
                notifyAdClick();
            }

            @Override
            public void onAdDismissed() {
                notifyAdClose();
            }

            @Override
            public void onAdFailed(String error) {
                notifyAdFail(new AdError(0,error));
            }
        });

    }
    @Override
    public void loadAd() {
        if (mInsertAd!=null){
            mInsertAd.loadAd();
        }
    }

    @Override
    public void showAd() {
        if (mInsertAd!=null&&isReady==true){
            mInsertAd.showAd(mActivity);
        }
    }

    @Override
    public void destroyAd() {
        if (mInsertAd!=null){
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
