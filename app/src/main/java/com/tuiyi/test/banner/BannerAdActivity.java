package com.tuiyi.test.banner;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.user.AllInAdListener;
import com.tuiyi.allin.user.AllInBannerAd;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.R;
import com.tuiyi.test.base.BaseAdActivity;

/**
 * 横幅广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class BannerAdActivity extends BaseAdActivity {

    private AllInBannerAd mAllInBannerAd;

    private ViewGroup mViewContain;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, BannerAdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewContain = findViewById(R.id.llContainer);
        findViewById(R.id.tvLoad).setOnClickListener(
                view -> showAd()
        );
    }

    @Override
    protected void initData() {
        initAd(TYPE_BANNER);
    }

    public void showAd() {
        super.showAd();
        saveAd(TYPE_BANNER);
        AdConfig adConfig = new AdConfig.Builder()
                .setPlaceId(mPid)
                .setWidth(mWidth)
                .setHeight(mHeight)
                .build();
        mAllInBannerAd = new AllInBannerAd(this, mViewContain, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {

                AllInToast.show(BannerAdActivity.this, error.getMessage());

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdReady() {
                mAllInBannerAd.showAd();
            }

            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdClose() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAllInBannerAd != null) {
            mAllInBannerAd.destroyAd();
        }
    }
}
