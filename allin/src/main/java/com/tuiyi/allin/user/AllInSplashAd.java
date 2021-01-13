package com.tuiyi.allin.user;

import android.app.Activity;
import android.view.ViewGroup;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.OnAdReloadListener;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.entity.AdSourceEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;
import com.tuiyi.allin.utlis.LocalUtils;
import com.tuiyi.allin.utlis.SysInfoUtils;

/**
 * AllIn闪屏广告
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class AllInSplashAd extends BaseAllInAd {

    private CustomSplashAd ad;

    /**
     * @param activity
     * @param container  view容器
     * @param adConfig   广告配置
     * @param adListener 广告监听
     */
    public AllInSplashAd(Activity activity, ViewGroup container, AdConfig adConfig, AllInAdListener adListener) {
        makeRequest(adConfig.placeId, adConfig.width, adConfig.height, SysInfoUtils.getDeviceInfo(activity), new AdNetCallBack() {
            @Override
            public void loadSuccess(AdEntity entity) {
                AdSourceEntity adSourceEntity = entity.adsource.get(0);
                adConfig.appId=adSourceEntity.appid;
                adConfig.thirdPid = adSourceEntity.placeid;
                adConfig.className=adSourceEntity.classname;
                adConfig.json=adSourceEntity.json;
                ad = AdFactory.getSplashAd(getAdType(adSourceEntity), adConfig);
                if (ad == null) {
                    adListener.onAdFailed(new AdError(AdErrorCode.UNKNOWN_AD_TYPE, "未知广告类型"));
                    return;
                }
                ad.setAdConfig(activity, adConfig, entity, adListener, new OnAdReloadListener() {
                    @Override
                    public void onAdReload(Activity activity, AdConfig adConfig,int currentPos, AdSourceEntity adSourceEntity) {
                        ad = AdFactory.getSplashAd(getAdType(adSourceEntity), adConfig);
                        if (ad == null) {
                            adListener.onAdFailed(new AdError(AdErrorCode.UNKNOWN_AD_TYPE, "未知广告类型"));
                            return;
                        }
                        ad.setAdConfig(activity, adConfig, entity, adListener, this);
                        ad.setCurrentAdPos(currentPos);
                        if (!LocalUtils.getAdIsAvailable(activity, adSourceEntity)) {
                            ad.notifyAdFail(new AdError(AdErrorCode.UN_RULES, "不符合规则"));
                            return;
                        } else {
                            LocalUtils.insertAd(activity, LocalUtils.getUnitId(adSourceEntity));
                        }
                        ad.setViewContainer(container);
                        ad.loadAd();
                    }
                });
                if (!LocalUtils.getAdIsAvailable(activity, adSourceEntity)) {
                    ad.notifyAdFail(new AdError(AdErrorCode.UN_RULES, "不符合规则"));
                    return;
                } else {
                    LocalUtils.insertAd(activity, LocalUtils.getUnitId(adSourceEntity));
                }
                ad.setViewContainer(container);
                ad.loadAd();
            }

            @Override
            public void loadFail(AdError error) {
                adListener.onAdFailed(error);
            }
        });

    }

    /**
     * 加载广告
     */
    @Override
    public void loadAd() {
        if (ad != null) {
            ad.loadAd();
        }
    }


    /**
     * 展示广告
     */
    @Override
    public void showAd() {
        if (ad != null) {
            ad.showAd();
        }
    }

    /**
     * 销毁广告
     */
    @Override
    public void destroyAd() {
        if (ad != null) {
            ad.removeAdCallBack();
            ad.destroyAd();
        }
    }

    /**
     * 暂停广告
     */
    @Override
    public void onPauseAd() {
        if (ad != null) {
            ad.onPauseAd();
        }
    }

    /**
     * 恢复广告
     */
    @Override
    public void onResumeAd() {
        if (ad != null) {
            ad.onResumeAd();
        }
    }

}
