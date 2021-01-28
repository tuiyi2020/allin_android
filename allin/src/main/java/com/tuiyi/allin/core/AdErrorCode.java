package com.tuiyi.allin.core;

/**
 * @author liuhuijie
 * @date 12/23/20
 */
public interface AdErrorCode {
    //网络错误
    int NET_ERROR = 10001;
    //无广告
    int NO_AD_ERROR = 20001;
    //缺少参数
    int PARAM_MISS = 30001;
    //未知类型广告
    int UNKNOWN_AD_TYPE = 40001;
    //不符合展示规则
    int UN_RULES = 50001;
    //渲染失败
    int RENDER_ERROR = 60001;
    //解析失败
    int PARSE_ERROR=70001;
    //三方未知错误
    int THIRD_UNKNOWN_ERROR=80001;
    //广告请求超时
    int TIME_OUT_ERROR=90001;

}
