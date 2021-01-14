package com.tuiyi.test.insert;

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
import com.tuiyi.allin.user.AllInInsertAd;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.R;
import com.tuiyi.test.base.BaseAdActivity;

/**
 * 插屏广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class InsertAdActivity extends BaseAdActivity {

    private AllInInsertAd mAllInInsertAd;

    private ViewGroup mViewContain;

    private Spinner mSpinner;

    private String mPlatformTypes[] = new String[]{
            "JD",
            "GDT",
            "TT"
    };

    private String mPlatformIds[] = new String[]{
            AdConstants.JD_INSERT_ID,
            AdConstants.GDT_INSERT_ID,
            AdConstants.TT_INSERT_ID
    };
    private int mCurrentPos;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, InsertAdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert;
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
        initAd(TYPE_INSERT);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                mPlatformTypes);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                AllInToast.show(InsertAdActivity.this,
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
        saveAd(TYPE_INSERT);
        AdConfig adConfig = new AdConfig.Builder()
                .setPlaceId(mPid)
                .setWidth(mWidth)
                .setHeight(mHeight)
                .build();

        mAllInInsertAd = new AllInInsertAd(this, adConfig, new AllInAdListener() {
            @Override
            public void onAdFailed(AdError error) {

                AllInToast.show(InsertAdActivity.this, error.getMessage());
            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onAdReady() {
                mAllInInsertAd.showAd();
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
        if (mAllInInsertAd != null) {
            mAllInInsertAd.destroyAd();
        }
    }
}
