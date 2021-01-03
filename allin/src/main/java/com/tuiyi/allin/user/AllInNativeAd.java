package com.tuiyi.allin.user;

import android.app.Activity;
import android.view.ViewGroup;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.entity.AdSourceEntity;
import com.tuiyi.allin.core.nativead.CustomNativeAd;
import com.tuiyi.allin.utlis.SysInfoUtils;

/**
 * 原生广告
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class AllInNativeAd extends BaseAllInAd {


    private CustomNativeAd ad;

    /**
     * @param activity
     * @param container  view容器
     * @param adConfig   广告配置
     * @param adListener 广告监听
     */
    public AllInNativeAd(Activity activity, ViewGroup container, AdConfig adConfig, AllInAdListener adListener) {
        makeRequest(adConfig.placeId, adConfig.width, adConfig.height, SysInfoUtils.getDeviceInfo(activity), new AdNetCallBack() {
            @Override
            public void loadSuccess(AdEntity entity) {
                AdSourceEntity adSourceEntity=entity.sourceid.get(0);
                adConfig.thirdPid=adSourceEntity.placeid;
                ad = AdFactory.getNativeAd(getAdType(adSourceEntity.sourceid), adConfig);
                if (ad == null) {
                    adListener.onAdFailed(new AdError(AdErrorCode.UNKNOWN_AD_TYPE, "未知广告类型"));
                    return;
                }
                ad.setActivity(activity);
                ad.setViewContainer(container);
                ad.setAdConfig(adConfig);
                ad.setAdCallback(adListener);
                ad.loadAd();
            }

            @Override
            public void loadFail(AdError error) {
                adListener.onAdFailed(error);
            }
        });

    }

    @Override
    public void loadAd() {
        if (ad != null) {
            ad.loadAd();
        }
    }

    @Override
    public void showAd() {
        if (ad != null) {
            ad.showAd();
        }
    }

    @Override
    public void destroyAd() {
        if (ad != null) {
            ad.destroyAd();
        }
    }

    @Override
    public void onPauseAd() {
        if (ad != null) {
            ad.onPauseAd();
        }
    }

    @Override
    public void onResumeAd() {
        if (ad != null) {
            ad.onResumeAd();
        }
    }
}
