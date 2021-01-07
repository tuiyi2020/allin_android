package com.tuiyi.allin.core.net;

/**
 * 接口地址
 *
 * @author liuhuijie
 * @date 12/23/20
 */
public interface NetApi {
   // String BASE_URL = "http://bj-l.adxvip.com/";
   // String BASE_URL="https://allin.51tuiyi.com/";
    String BASE_URL="https://allinapi.51tuiyi.com/";
    String GET_CONFIG = BASE_URL + "sapi.LoadConfiguration";
    String REQUEST_LOG = BASE_URL + "sapi.RequestLogServlet";
    String REQUEST_FAIL_LOG = BASE_URL + "sapi.RequestFailLogServlet";
    String SHOW_LOG = BASE_URL + "sapi.ShowLogServlet";
    String CLICK_LOG = BASE_URL + "sapi.ClickLogServlet";
}
