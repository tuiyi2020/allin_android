package com.tuiyi.allin.user;

import com.tuiyi.allin.core.AdError;
import com.tuiyi.allin.core.entity.AdEntity;

/**
 * allIn 数据回调
 *
 * @author liuhuijie
 * @date 12/25/20
 */
public interface AdNetCallBack {

    void loadSuccess(AdEntity entity);

    void loadFail(AdError error);

}
