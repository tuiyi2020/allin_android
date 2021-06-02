package com.tuiyi.test.splash;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.user.AllInAdListener;
import com.tuiyi.allin.user.AllInSplashAd;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.R;
import com.tuiyi.test.base.BaseActivity;

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

    public static void startActivity(Activity activity, String placeId, int width, int height) {
        Intent intent = new Intent(activity, SplashAdShowActivity.class);
        intent.putExtra("placeId", placeId);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
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
        AdConfig adConfig = new AdConfig.Builder().setPlaceId(mPlaceId)
                .setShowTime(12)
                .setWidth(getIntent().getIntExtra("width", 0))
                .setHeight(getIntent().getIntExtra("height", 0))
                .build();
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
