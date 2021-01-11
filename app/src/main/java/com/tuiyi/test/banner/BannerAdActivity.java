package com.tuiyi.test.banner;

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

    private Spinner mSpinner;

    private String mPlatformTypes[] = new String[]{
            "JD",
            "GDT",
            "TT"
    };

    private String mPlatformIds[] = new String[]{
            AdConstants.JD_BANNER_ID,
            AdConstants.GDT_BANNER_ID,
            AdConstants.TT_BANNER_ID
    };
    private int mCurrentPos;


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
        mSpinner = findViewById(R.id.spPlatform);
        mViewContain = findViewById(R.id.llContainer);
        findViewById(R.id.tvLoad).setOnClickListener(
                view -> showAd()
        );
    }

    @Override
    protected void initData() {
        initAd(TYPE_BANNER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                mPlatformTypes);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                AllInToast.show(BannerAdActivity.this,
                        parent.getItemAtPosition(position).toString());
                mCurrentPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void showAd() {
        super.showAd();
        saveAd(TYPE_BANNER);
        AdConfig adConfig = new AdConfig.Builder().setPlaceId(mPid).build();
        adConfig.width = mWidth;
        adConfig.height = mHeight;
/*        adConfig.width = ScreenUtils.getScreenWidth(this);
        adConfig.height = (adConfig.width * 100) / 360;
        switch (mPlatformIds[mCurrentPos]) {
            case AdConstants.JD_BANNER_ID:
                adConfig.width = 600;
                adConfig.height = 150;
                break;
        }*/
        mAllInBannerAd = new AllInBannerAd(this, mViewContain, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {


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
