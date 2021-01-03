package com.tuiyi.test.splash;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tuiyi.allin.user.AdConstants;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.BaseActivity;
import com.tuiyi.test.R;

/**
 * 开屏广告
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public class SplashAdActivity extends BaseActivity {


    private Spinner mSpinner;

    private String mPlatformTypes[] = new String[]{
            "JD",
            "GDT",
            "TT"
    };

    private String mPlatformIds[] = new String[]{
            AdConstants.JD_SPLASH_ID,
            AdConstants.GDT_SPLASH_ID,
            AdConstants.TT_SPLASH_ID
    };
    private int mCurrentPos;

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

        mSpinner = findViewById(R.id.spPlatform);
        findViewById(R.id.tvLoad).setOnClickListener(
                view -> SplashAdShowActivity.startActivity(SplashAdActivity.this, mPlatformIds[mCurrentPos])
        );

    }


    @Override
    protected void initData() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,
                mPlatformTypes);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                AllInToast.show(SplashAdActivity.this,
                        parent.getItemAtPosition(position).toString());
                mCurrentPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

}
