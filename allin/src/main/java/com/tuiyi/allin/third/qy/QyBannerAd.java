package com.tuiyi.allin.third.qy;

import com.mcto.sspsdk.IQYNative;
import com.mcto.sspsdk.IQyBanner;
import com.mcto.sspsdk.QyAdSlot;
import com.mcto.sspsdk.QyBannerStyle;
import com.mcto.sspsdk.QyClient;
import com.mcto.sspsdk.QyClientInfo;
import com.tuiyi.allin.core.bannerad.CustomBannerAd;
import com.tuiyi.allin.core.entity.AdEntity;

import cn.imeiadx.jsdk.util.OaidManager;

/**
 * @author liuhuijie
 * @date 1/27/21
 */
public class QyBannerAd extends CustomBannerAd {

    private IQYNative adNative;

    private QyBannerStyle[] mBannerStyleArrays = new QyBannerStyle[]{
            QyBannerStyle.QYBANNER_FULL,
            QyBannerStyle.QYBANNER_TITLEIN,
            QyBannerStyle.QYBANNER_TITLEBELOW,
            QyBannerStyle.QYBANNER_TITLEABOVE,
            QyBannerStyle.QYBANNER_STRIP};

    public QyBannerAd(){

    }

    @Override
    public void customAdMessage(AdEntity adEntity) {

    }

    @Override
    public void loadAd() {
        //mAdConfig.appId="1631902583015428";
        //mAdConfig.thirdPid="1628916542140418";
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
       // String codeId = mShowBannerStyle == 4 ? "1641907803270151" : "1628916503204866";
        //QyBannerStyle bannerStyle = Arrays.asList(mBannerStyleArrays).get(mShowBannerStyle++);
        String codeId = "1628916503204866";
        QyBannerStyle bannerStyle=mBannerStyleArrays[0];
        QyAdSlot mQyAdSlot = QyAdSlot.newQyAdSlot()
                .codeId(codeId)
                //.channelId(1122)
                .bannerStyle(bannerStyle)
                .supportDeeplink(true)
                .build();
        adNative.loadBannerAd(mQyAdSlot, new IQYNative.BannerAdListener() {
            @Override
            public void onError(int var1) {
            }

            @Override
            public void onBannerAdLoad(IQyBanner ad) {
                if (ad == null || ad.getBannerView() == null) {
                    //AllInToast.show(TestBannerActivity.this, "ad is null!");
                    return;
                }
                mViewContainer.removeAllViews();
                mViewContainer.addView(ad.getBannerView());
                bindAdListener(ad);
            }
        });
    }
    private void bindAdListener(final IQyBanner ad) {
        ad.setBannerInteractionListener(new IQyBanner.IAdInteractionListener() {
            @Override
            public void onAdShow() {
                notifyAdShow();
               // Toast.makeText(getApplicationContext(), "广告展示", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                notifyAdClick();
               // Toast.makeText(getApplicationContext(), "广告被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRenderSuccess() {
                notifyAdReady();
                //Toast.makeText(getApplicationContext(), "广告渲染成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdStart() {
               // Toast.makeText(getApplicationContext(), "onAdStart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdStop() {
               // Toast.makeText(getApplicationContext(), "onAdStop", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdComplete() {
               // Toast.makeText(getApplicationContext(), "onAdComplete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdPlayError() {
                //Toast.makeText(getApplicationContext(), "onAdPlayError", Toast.LENGTH_SHORT).show();
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
