package com.tuiyi.test.nativead;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tuiyi.allin.core.AdConfig;
import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.user.AdConstants;
import com.tuiyi.allin.user.AllInAdListener;
import com.tuiyi.allin.user.AllInNativeAd;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.R;
import com.tuiyi.test.base.BaseAdActivity;

/**
 * 原生广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class NativeAdActivity extends BaseAdActivity {

    private AllInNativeAd mAllInNativeAd;

    private ViewGroup mViewContain;

    private Spinner mSpinner;

    private String mPlatformTypes[] = new String[]{
            "JD",
            "GDT",
            "TT"
    };

    private String mPlatformIds[] = new String[]{
            AdConstants.JD_NATIVE_ID,
            AdConstants.GDT_NATIVE_ID,
            AdConstants.TT_NATIVE_ID
    };
    private int mCurrentPos;


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
        super.initView();
        mSpinner = findViewById(R.id.spPlatform);
        mViewContain = findViewById(R.id.llContainer);
        findViewById(R.id.tvLoad).setOnClickListener(
                view -> showAd()
        );

    }

    @Override
    protected void initData() {
        initAd(TYPE_NATIVE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                mPlatformTypes);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                AllInToast.show(NativeAdActivity.this,
                        parent.getItemAtPosition(position).toString());
                mCurrentPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void showAd() {
        super.showAd();
        saveAd(TYPE_NATIVE);
        AdConfig adConfig = new AdConfig.Builder()
                .setPlaceId(mPid)
                .setWidth(mWidth)
                .setHeight(mHeight)
                .build();
        mAllInNativeAd = new AllInNativeAd(this, mViewContain, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {
                AllInToast.show(NativeAdActivity.this, error.getMessage());

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdReady() {
                mAllInNativeAd.showAd();
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
        if (mAllInNativeAd != null) {
            mAllInNativeAd.destroyAd();
        }
    }
}
