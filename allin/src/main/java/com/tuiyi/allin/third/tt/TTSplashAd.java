package com.tuiyi.allin.third.tt;

import android.view.View;

import androidx.annotation.MainThread;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.splashad.CustomSplashAd;
import com.tuiyi.allin.utlis.AllInLog;

/**
 * 头条闪屏广告
 * @author liuhuijie
 * @date 12/6/20
 */
public class TTSplashAd extends CustomSplashAd {

    private TTAdNative mTTAdNative;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;

    public TTSplashAd(){

    }
    @Override
    public void loadAd() {
        TTAdManagerHolder.init(mActivity.getApplication(), mAdConfig.appId);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(mActivity);
        String mCodeId = mAdConfig.thirdPid;
        boolean mIsExpress=false;
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            //float expressViewWidth = UIUtils.getScreenWidthDp(this);
            //float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    //view宽高等于图片的宽高
                    .setExpressViewAcceptedSize(mAdConfig.width,mAdConfig.height)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setImageAcceptedSize(mAdConfig.width, mAdConfig.height)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                AllInLog.i("code"+code+"message"+message);
            }

            @Override
            @MainThread
            public void onTimeout() {
                AllInLog.i("开屏广告加载超时");
            }

            @Override
            @MainThread
            public void onSplashAdLoad(com.bytedance.sdk.openadsdk.TTSplashAd ad) {
                AllInLog.i( "开屏广告请求成功");
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null && mViewContainer != null &&mActivity!=null&& !mActivity.isFinishing()) {
                    mViewContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mViewContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                }else {

                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new com.bytedance.sdk.openadsdk.TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        AllInLog.i("onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        AllInLog.i("onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        AllInLog.i("onAdSkip");

                    }

                    @Override
                    public void onAdTimeOver() {
                        AllInLog.i("onAdTimeOver");
                    }
                });
                if(ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                AllInLog.i("下载中...");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            AllInLog.i("下载暂停...");

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            AllInLog.i("下载失败...");

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            AllInLog.i("下载完成...");

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            AllInLog.i("安装完成...");

                        }
                    });
                }
            }
        }, AD_TIME_OUT);
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
