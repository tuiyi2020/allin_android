package com.tuiyi.allin.third.baidu;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;

import org.json.JSONObject;

/**
 * 百度横幅
 * @author liuhuijie
 * @date 12/21/20
 */
public class BdBannerAd extends CustomBannerAd {

    private Activity mActivity;

    private ViewGroup mViewGroup;

    private AdView mAdView;



    public BdBannerAd(Activity activity, ViewGroup viewGroup,String posId,String appId){
        mActivity=activity;
        mViewGroup=viewGroup;
        mAdView = new AdView(activity, posId);
        AdView.setAppSid(activity, appId);
        mAdView.setListener(new AdViewListener() {
            @Override
            public void onAdSwitch() {
            }

            @Override
            public void onAdShow(JSONObject info) {
                notifyAdShow();
            }

            @Override
            public void onAdReady(AdView adView) {
                notifyAdReady();
            }

            @Override
            public void onAdFailed(String reason) {
                notifyAdFail(new AdError(0,reason));
            }

            @Override
            public void onAdClick(JSONObject info) {
                notifyAdClick();
            }

            @Override
            public void onAdClose(JSONObject arg0) {
                notifyAdClose();
            }
        });

    }

    @Override
    public void loadAd() {
        mViewGroup.removeAllViews();
        int scaleWidth=300;
        int scaleHeight=100;
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width * scaleHeight / scaleWidth;
        ViewGroup.LayoutParams rllp = new ViewGroup.LayoutParams(width, height);
        mViewGroup.addView(mAdView, rllp);
    }

    @Override
    public void showAd() {

    }

    @Override
    public void destroyAd() {
        if (mAdView!=null){
            mAdView.destroy();
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
