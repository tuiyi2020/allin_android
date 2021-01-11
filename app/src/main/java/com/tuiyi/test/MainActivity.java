package com.tuiyi.test;

import android.view.View;

import com.tuiyi.test.banner.BannerAdActivity;
import com.tuiyi.test.base.BaseActivity;
import com.tuiyi.test.insert.InsertAdActivity;
import com.tuiyi.test.nativead.NativeAdActivity;
import com.tuiyi.test.splash.SplashAdActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tvSplash).setOnClickListener(this);
        findViewById(R.id.tvBanner).setOnClickListener(this);
        findViewById(R.id.tvInsert).setOnClickListener(this);
        findViewById(R.id.tvNative).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSplash:
                SplashAdActivity.startActivity(MainActivity.this);
                break;
            case R.id.tvBanner:
                BannerAdActivity.startActivity(MainActivity.this);
                break;
            case R.id.tvInsert:
                InsertAdActivity.startActivity(MainActivity.this);
                break;
            case R.id.tvNative:
                NativeAdActivity.startActivity(MainActivity.this);
                break;
            default:
                break;
        }
    }
}