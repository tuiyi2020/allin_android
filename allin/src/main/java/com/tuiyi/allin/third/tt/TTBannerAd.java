package com.tuiyi.allin.third.tt;

import android.view.View;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.utlis.AllInLog;
import com.tuiyi.allin.utlis.AllInToast;

import java.util.List;

/**
 * 头条轮播图广告
 *
 * @author liuhuijie
 * @date 12/20/20
 */
public class TTBannerAd extends CustomBannerAd {

    private TTAdNative mTTAdNative;

    private TTNativeExpressAd mTTAd;


    public TTBannerAd() {

    }

    @Override
    public void loadAd() {
        TTAdManagerHolder.init(mActivity.getApplication(), mAdConfig.appId);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(mActivity);
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(mActivity);
        loadExpressAd(mAdConfig.thirdPid, mAdConfig.width, mAdConfig.height);
    }

    @Override
    public void showAd() {

        if (mTTAd != null) {
            mTTAd.render();
        } else {
            AllInToast.show(mActivity, "请先加载广告..");
        }
    }

    @Override
    public void destroyAd() {
        if (mTTAd != null) {
            mTTAd.destroy();
        }
    }

    @Override
    public void onPauseAd() {

    }

    @Override
    public void onResumeAd() {

    }

    private void loadExpressAd(String codeId, int expressViewWidth, int expressViewHeight) {
        mViewContainer.removeAllViews();
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                AllInLog.i("code" + code + "message" + message);
                notifyAdFail(new AdError(code, message));
                mViewContainer.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(30 * 1000);
                bindAdListener(mTTAd);
                AllInLog.i("load success!");
            }
        });
    }

    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                notifyAdClick();
                AllInLog.i("广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                notifyAdShow();
                AllInLog.i("广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                AllInLog.i(msg + " code:" + code);
                notifyAdFail(new AdError(code,msg));
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                AllInLog.i("渲染成功");
                notifyAdReady();
                mViewContainer.removeAllViews();
                mViewContainer.addView(view);
            }
        });
        //dislike设置
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                AllInLog.i("点击开始下载");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
            }

            @Override
            public void onInstalled(String fileName, String appName) {
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
            }
        });
    }

    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(mActivity, words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(mActivity, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //TToast.show(mContext, "反馈了 " + value);
                AllInToast.show(mActivity, "\t\t\t\t\t\t\t感谢您的反馈!\t\t\t\t\t\t\n我们将为您带来更优质的广告体验", 3);
            }

            @Override
            public void onCancel() {
                AllInToast.show(mActivity, "点击取消 ");
            }

            @Override
            public void onRefuse() {
                AllInToast.show(mActivity, "您已成功提交反馈，请勿重复提交哦！", 3);
            }

        });
    }

    @Override
    public void customAdMessage(AdEntity adEntity) {

    }
}
