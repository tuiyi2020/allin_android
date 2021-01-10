package com.tuiyi.test.splash;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.user.AdConstants;
import com.tuiyi.allin.user.AllInAdListener;
import com.tuiyi.allin.user.AllInSplashAd;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.BaseActivity;
import com.tuiyi.test.R;

/**
 * 开屏展示页面
 *
 * @author liuhuijie
 * @date 1/2/21
 */
public class SplashAdShowActivity extends BaseActivity {

    private AllInSplashAd mAllInSplashAd;

    private ViewGroup mViewContain;

    private String mPlaceId;

    public static void startActivity(Activity activity, String placeId) {
        Intent intent = new Intent(activity, SplashAdShowActivity.class);
        intent.putExtra("placeId", placeId);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_show;
    }

    @Override
    protected void initView() {
        mViewContain = findViewById(R.id.llContainer);
    }

    @Override
    protected void initData() {
        mPlaceId = getIntent().getStringExtra("placeId");
        showAd();
    }

    private void showAd() {
        AdConfig adConfig = new AdConfig.Builder().setPlaceId(mPlaceId).build();
        adConfig.width = 540;
        adConfig.height = 720;
        switch (mPlaceId) {
            case AdConstants.JD_SPLASH_ID:
                adConfig.width = 320;
                adConfig.height = 480;
                break;
        }
        mAllInSplashAd = new AllInSplashAd(this, mViewContain, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {
                AllInToast.show(SplashAdShowActivity.this, error.getMessage());
                finish();

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdReady() {
                mAllInSplashAd.showAd();
            }

            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdClose() {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAllInSplashAd != null) {
            mAllInSplashAd.destroyAd();
        }

    }
}
