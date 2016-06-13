package cn.rainbow.sdk.analytics.data.remote;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bvin on 2016/6/12.
 */
public interface Api {

    String HOST_TEST = "http://192.168.163.162:8010/";//http://dev.report.honglingjin.cn
    String HOST_OFFCIAL = "http://report.honglingjin.cn ";
    String HOST = HOST_TEST;

    String URL_APP_PV = "report_apv";
    String URL_GOODS_PV = "report_gpv";
    String URL_CART = "report_cart";
    String URL_FAV = "report_favorite";
    String URL_ORDER = "report_order";

    /**
     * APP-PV上报.
     * @param channel 渠道id
     * @param merchant 商户id，不传默认为1：天虹
     * @param url 当前url地址 此值需要urlencode
     * @param version 当前APP版本
     * @param enterTime 进入页面时间 格式：2015-05-11 11:00:00
     * @param leavingTime 离开页面时间 格式：2015-05-11 11:10:00
     * @param device 访客手机品牌，例如：huawei mt2-c00
     * @param os 访客手机系统类型
     * @param osVersion 手机系统版本
     * @param id 访客设备标示
     * @return
     */
    @GET(URL_APP_PV)
    Call<Model> reportAPV(@Query("c") String channel, @Query("mid") String merchant, @Query("u") String url, @Query("v") String version,
                          @Query("et") String enterTime, @Query("lt") String leavingTime, @Query("mb") String
                                  device, @Query("o") String os, @Query("ov") String osVersion, @Query("id") String id);


    @GET(URL_GOODS_PV)
    Call<Model> reportGPV(@Query("c") String channel, @Query("mid") String merchant, @Query("gid") String goodsId, @Query("gn") String goodsName,
                          @Query("gi") String goodsImage, @Query("et") String enterTime, @Query("lt") String leavingTime, @Query("gc1") String
                                  goodsCategory1, @Query("gc1") String goodsCategory2, @Query("gc1") String goodsCategory3, @Query("id") String id,
                          @Query("uid") String uid);
}
