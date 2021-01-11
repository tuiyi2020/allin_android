package com.tuiyi.test.base;

import android.text.TextUtils;
import android.widget.EditText;

import com.tuiyi.allin.user.AdConstants;
import com.tuiyi.allin.utlis.AllInToast;
import com.tuiyi.test.PreferencesUtils;
import com.tuiyi.test.R;


/**
 * 基础activity
 *
 * @author liuhuijie
 * @date 12/24/20
 */
public abstract class BaseAdActivity extends BaseActivity {

    public final static int TYPE_SPLASH = 1;
    public final static int TYPE_INSERT = 2;
    public final static int TYPE_BANNER = 3;
    public final static int TYPE_NATIVE = 4;

    protected EditText mEtPid, mEtWidth, mEtHeight;
    protected int mWidth, mHeight;
    protected String mPid;


    @Override
    protected void initView() {
        mEtPid = findViewById(R.id.etPid);
        mEtWidth = findViewById(R.id.etWidth);
        mEtHeight = findViewById(R.id.etHeight);
    }

    public void initAd(int type) {
        String pidKey = "";
        String defaultPidValue = "";
        String heightKey = "";
        String widthKey = "";
        switch (type) {
            case TYPE_SPLASH:
                pidKey = PreferencesUtils.SPLASH_PID;
                defaultPidValue = AdConstants.NORMAL_SPLASH_ID;
                heightKey = PreferencesUtils.SPLASH_HEIGHT;
                widthKey = PreferencesUtils.SPLASH_WIDTH;
                break;
            case TYPE_BANNER:
                pidKey = PreferencesUtils.BANNER_PID;
                defaultPidValue = AdConstants.NORMAL_BANNER_ID;
                heightKey = PreferencesUtils.BANNER_HEIGHT;
                widthKey = PreferencesUtils.BANNER_WIDTH;
                break;
            case TYPE_INSERT:
                pidKey = PreferencesUtils.INSERT_PID;
                defaultPidValue = AdConstants.NORMAL_INSERT_ID;
                heightKey = PreferencesUtils.INSERT_HEIGHT;
                widthKey = PreferencesUtils.INSERT_WIDTH;
                break;
            case TYPE_NATIVE:
                pidKey = PreferencesUtils.NATIVE_PID;
                defaultPidValue = AdConstants.NORMAL_NATIVE_ID;
                heightKey = PreferencesUtils.NATIVE_HEIGHT;
                widthKey = PreferencesUtils.NATIVE_WIDTH;
                break;
            default:
                break;
        }
        mPid = PreferencesUtils.get(pidKey, defaultPidValue).toString();
        mEtPid.setText(mPid);
        mHeight = Integer.parseInt(PreferencesUtils.get(heightKey, -1).toString());
        mWidth = Integer.parseInt(PreferencesUtils.get(widthKey, -1).toString());
        mEtWidth.setText(String.valueOf(mWidth));
        mEtHeight.setText(String.valueOf(mHeight));
    }

    public void saveAd(int type) {
        String pidKey = "";
        String heightKey = "";
        String widthKey = "";
        switch (type) {
            case TYPE_SPLASH:
                pidKey = PreferencesUtils.SPLASH_PID;
                heightKey = PreferencesUtils.SPLASH_HEIGHT;
                widthKey = PreferencesUtils.SPLASH_WIDTH;
                break;
            case TYPE_BANNER:
                pidKey = PreferencesUtils.BANNER_PID;
                heightKey = PreferencesUtils.BANNER_HEIGHT;
                widthKey = PreferencesUtils.BANNER_WIDTH;
                break;
            case TYPE_INSERT:
                pidKey = PreferencesUtils.INSERT_PID;
                heightKey = PreferencesUtils.INSERT_HEIGHT;
                widthKey = PreferencesUtils.INSERT_WIDTH;
                break;
            case TYPE_NATIVE:
                pidKey = PreferencesUtils.NATIVE_PID;
                heightKey = PreferencesUtils.NATIVE_HEIGHT;
                widthKey = PreferencesUtils.NATIVE_WIDTH;
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(mPid)) {
            PreferencesUtils.put(pidKey, mPid);
            PreferencesUtils.put(widthKey, mWidth);
            PreferencesUtils.put(heightKey, mHeight);
        }
    }

    public void showAd() {
        mPid = mEtPid.getText().toString().trim();
        if (TextUtils.isEmpty(mPid)) {
            AllInToast.show(this, "请输入位置id");
            return;
        }
        mHeight = Integer.parseInt(mEtHeight.getText().toString().trim());
        mWidth = Integer.parseInt(mEtWidth.getText().toString().trim());
    }

}
