package com.tuiyi.test.splash;

import android.app.Activity;
import android.content.Intent;

import com.tuiyi.test.R;
import com.tuiyi.test.base.BaseAdActivity;

/**
 * 开屏广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class SplashAdActivity extends BaseAdActivity {


    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, SplashAdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.tvLoad).setOnClickListener(
                view -> showAd()
        );


    }


    @Override
    protected void initData() {
        initAd(TYPE_SPLASH);

    }

    @Override
    public void showAd() {
        super.showAd();
        saveAd(TYPE_SPLASH);
        SplashAdShowActivity.startActivity(SplashAdActivity.this, mPid, mWidth, mHeight);
    }

}
