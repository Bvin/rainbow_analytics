package cn.rainbow.sdk.analytics.data.remote;

import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/14.
 */
public interface ApiConfig {

    String HOST_TEST = "http://192.168.148.162:8010/";//http://dev.report.honglingjin.cn
    String HOST_OFFICIAL = "http://report.honglingjin.cn";
    String HOST = THAnalytics.getCurrentConfig().isTestEnv() ? HOST_TEST : HOST_OFFICIAL;

    String URL_REPORT = "report";
    String URL_APP_PV = URL_REPORT;
    String URL_GOODS_PV = URL_REPORT;
    String URL_CART = URL_REPORT;
    String URL_FAV = URL_REPORT;
    String URL_ORDER = URL_REPORT;
    String URL_EVENT = URL_REPORT;
}
