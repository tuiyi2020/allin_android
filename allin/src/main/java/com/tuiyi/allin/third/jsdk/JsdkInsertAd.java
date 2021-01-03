package com.tuiyi.allin.third.jsdk;

import android.graphics.drawable.ColorDrawable;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;
import com.tuiyi.allin.core.insertad.CustomInsertAd;

import cn.imeiadx.jsdk.jy.mob.JyAd;
import cn.imeiadx.jsdk.jy.mob.JyAdListener2;
import cn.imeiadx.jsdk.jy.mob.JyAdPopWindow;
import cn.imeiadx.jsdk.jy.mob.JyNative;

/**
 * 推易插屏
 *
 * @author liuhuijie
 * @date 2020/11/20
 */
public class JsdkInsertAd extends CustomInsertAd {

    private JyAdListener2 mJyAdListener2;
    private JyAdPopWindow mJyAdPopWindow;

    public JsdkInsertAd() {

    }

    @Override
    public void loadAd() {
        mAdConfig.thirdPid = "GL2TTLZJK3JTFWXECFJ1";
        mAdConfig.width = -1;
        mAdConfig.height = -1;
        if (mJyAdPopWindow!=null){
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
                // notifyAdReady();
            }

            @Override
            public void onADReceive(JyNative jyNative) {
                super.onADReceive(jyNative);
                //notifyAdReady();
            }

            @Override
            public void onNoAD(String err) {
                super.onNoAD(err);
                notifyAdFail(new AdError(0, err));
            }

            @Override
            public void onClosed() {
                super.onClosed();
                if (mJyAdPopWindow!=null){
                    mJyAdPopWindow.dismiss();
                    mJyAdPopWindow= null;
                }
                notifyAdClose();
            }

            @Override
            public void onADReceive() {
                super.onADReceive();
                notifyAdReady();
            }
        };
        mJyAdPopWindow = JyAd.initPopWindow(mActivity
                , mAdConfig.thirdPid, mAdConfig.width, mAdConfig.height, mJyAdListener2,
                new ColorDrawable(0x7DC0C0C0), 640, 960, true);
        mJyAdPopWindow.setOpen(false);
    }

    @Override
    public void showAd() {

    }

    @Override
    public void destroyAd() {
        if (mJyAdPopWindow != null) {
            mJyAdPopWindow.dismiss();
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
