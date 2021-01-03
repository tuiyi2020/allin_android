package com.tuiyi.test.nativead;

import android.app.Activity;
import android.content.Intent;
import android.widget.FrameLayout;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.user.AdConstants;
import com.tuiyi.allin.user.AllInAdListener;
import com.tuiyi.allin.user.AllInNativeAd;
import com.tuiyi.test.BaseActivity;
import com.tuiyi.test.R;

/**
 * 原生广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class NativeAdActivity extends BaseActivity {

    private AllInNativeAd mAllInNativeAd;

    private FrameLayout mViewContain;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, NativeAdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_native;
    }

    @Override
    protected void initView() {

        mViewContain = findViewById(R.id.flContainer);

    }

    @Override
    protected void initData() {
        AdConfig adConfig = new AdConfig.Builder().setPlaceId(AdConstants.JD_NATIVE_ID).build();
        mAllInNativeAd = new AllInNativeAd(this, mViewContain, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {


            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdReady() {

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
        if (mAllInNativeAd!=null){
            mAllInNativeAd.destroyAd();
        }
    }
}
