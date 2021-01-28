package com.tuiyi.allin.third.qy;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mcto.sspsdk.IQYNative;
import com.mcto.sspsdk.IQySplash;
import com.mcto.sspsdk.QyAdSlot;
import com.mcto.sspsdk.QyClient;
import com.mcto.sspsdk.QyClientInfo;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.AdErrorCode;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;

import cn.imeiadx.jsdk.util.OaidManager;

/**
 * 爱奇艺开屏
 * @author liuhuijie
 * @date 1/27/21
 */
public class QySplashAd extends CustomSplashAd {

    private IQYNative adNative;

    public QySplashAd(){


    }

    @Override
    public void customAdMessage(AdEntity adEntity) {
    }

    @Override
    public void loadAd() {
       // mAdConfig.appId="1631902583015428";
       // mAdConfig.thirdPid="1628916542140418";
        QyAdManagerHolder.init(mActivity,mAdConfig.appId);
        QyClient qyClient = QyAdManagerHolder.get();
        //设置用户属性，全局的
        qyClient.setClientInfo(QyClientInfo.newQyAdsClientInfo()
                //设置经纬度信息，可选
                // .latitude(55)
                // .longitude(48)
                //设置OAID，需要app接入信通院的SDK，强烈建议设置此值
                // TODO: 1/27/21
                .oaid(OaidManager.getOaid(mActivity))
                //设置用户的年龄区间，可选
                // .userAge(601)
                //设置用户的性别，可选
                // .userSex(FEMALE)
                .build());

        adNative = qyClient.createAdNative(mActivity);
        //设置广告位属性
        QyAdSlot slot = QyAdSlot.newQyAdSlot()
                //是否支持开机屏广告预请求功能，开启该功能时，SDK会提前预缓存少量开机屏广告素材
                //.supportPreRequest("1".equals(SharedPreferencesFactory.get(this).get("splashSupportPreRequest")))
                //广告位id
                .codeId(mAdConfig.thirdPid)
                //开机屏底部logo
                // .splashLogo(R.drawable.ic_launcher_foreground)
                //开机屏广告请求超时时长，不设置的话，SDK默认3秒，开启预请求时建议不少于1.3秒，不开启预请求时建议不少于3秒
                .timeout(3000)
                .build();
        //请求广告
        adNative.loadSplashAd(slot, new IQYNative.SplashAdListener() {
            @Override
            public void onError(int var1) {
                notifyAdFail(new AdError(AdErrorCode.THIRD_UNKNOWN_ERROR,"qyError"+var1));
            }

            @Override
            public void onTimeout() {
                notifyAdFail(new AdError(AdErrorCode.TIME_OUT_ERROR,"qy请求超时"));
            }

            @Override
            public void onSplashAdLoad(IQySplash var1) {
                mViewContainer.removeAllViews();
                mViewContainer.addView(var1.getSplashView(), new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));

                var1.setSplashInteractionListener(new IQySplash.IAdInteractionListener() {
                    @Override
                    public void onAdClick() {
                        notifyAdClick();
                    }

                    @Override
                    public void onAdShow() {
                        notifyAdShow();
                    }

                    @Override
                    public void onAdSkip() {
                       notifyAdClose();
                    }

                    @Override
                    public void onAdTimeOver() {
                        notifyAdClose();
                    }
                });
            }
        });
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
}
