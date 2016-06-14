package cn.rainbow.sdk.analytics.data.remote;

/**
 * Created by bvin on 2016/6/14.
 */
public interface ApiConfig {

    String HOST_TEST = "http://192.168.163.162:8010/";//http://dev.report.honglingjin.cn
    String HOST_OFFCIAL = "http://report.honglingjin.cn ";
    String HOST = HOST_TEST;

    String URL_APP_PV = "report_apv";
    String URL_GOODS_PV = "report_gpv";
    String URL_CART = "report_cart";
    String URL_FAV = "report_favorite";
    String URL_ORDER = "report_order";
}
