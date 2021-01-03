package com.tuiyi.allin.third.baidu;

import android.app.Activity;
import android.view.ViewGroup;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashLpCloseListener;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

/**
 * 百度闪屏
 *
 * @author liuhuijie
 * @date 2020/12/10
 */
public class BdSplashAd extends CustomSplashAd {
    private SplashAd mSplashAd;


    public BdSplashAd(Activity activity, ViewGroup viewGroup,String appId, String posId) {
        AdSettings.setSupportHttps(true);
        AdView.setAppSid(activity, appId);
        SplashLpCloseListener listener = new SplashLpCloseListener() {
            @Override
            public void onLpClosed() {
            }

            @Override
            public void onAdDismissed() {


            }

            @Override
            public void onAdFailed(String arg0) {

            }

            @Override
            public void onAdPresent() {
            }

            @Override
            public void onAdClick() {
            }
        };

        mSplashAd = new SplashAd(activity, viewGroup, listener, posId, true);
    }

    @Override
    public void loadAd() {

    }

    @Override
    public void showAd() {
    }

    @Override
    public void destroyAd() {
        if (mSplashAd!=null){
            mSplashAd.destroy();
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
