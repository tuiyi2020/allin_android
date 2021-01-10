package com.tuiyi.allin.core;

import android.app.Activity;

import com.tuiyi.allin.core.entity.AdSourceEntity;

/**
 * 广告加载下一条监听
 *
 * @author liuhuijie
 * @date 1/7/21
 */
public interface OnAdReloadListener {
    void onAdReload(Activity activity, AdConfig adConfig,int currentPos, AdSourceEntity adSourceEntity);
}
