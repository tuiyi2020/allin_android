package com.tuiyi.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.tuiyi.test.banner.BannerAdActivity;
import com.tuiyi.test.base.BaseActivity;
import com.tuiyi.test.insert.InsertAdActivity;
import com.tuiyi.test.nativead.NativeAdActivity;
import com.tuiyi.test.splash.SplashAdActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //读写权限
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };
    //请求状态码
    private static final int REQUEST_PERMISSION_CODE = 1;
    private void initPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
       // checkIsAndroidO();
    }
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
        initPermission();
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