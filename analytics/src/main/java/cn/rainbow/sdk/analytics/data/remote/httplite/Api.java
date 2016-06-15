package cn.rainbow.sdk.analytics.data.remote.httplite;

import alexclin.httplite.annotation.GET;
import alexclin.httplite.annotation.Param;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.Model;

/**
 * Created by bvin on 2016/6/14.
 */

public interface Api {


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

    @GET(ApiConfig.URL_APP_PV)
    void reportAPV(@Param("c") int channel, @Param("mid") String merchant, @Param("u") String url, @Param("v") String version,
                    @Param("et") String enterTime, @Param("lt") String leavingTime, @Param("mb") String
                                  device, @Param("o") String os, @Param("ov") String osVersion, @Param("id") String id, Callback<Model> callback);


    @GET(ApiConfig.URL_GOODS_PV)
    void reportGPV(@Param("c") int channel, @Param("mid") String merchant, @Param("gid") String goodsId, @Param("gn") String goodsName,
                          @Param("gi") String goodsImage, @Param("et") String enterTime, @Param("lt") String leavingTime, @Param("gc1") String
                                  goodsCategory1, @Param("gc1") String goodsCategory2, @Param("gc1") String goodsCategory3, @Param("id") String id,
                          @Param("uid") String uid, Callback<Model> callback);

    @GET(ApiConfig.URL_CART)
    void reportCart(@Param("c") int channel, @Param("mid") String merchant, @Param("gid") String goodsId, @Param("gsku") String
            goodsSkuCode, @Param("gn") String goodsName, @Param("gi") String goodsImage, @Param("gp") String goodsPrice,
                           @Param("ga") String goodsSellPrice, @Param("gc") String goodsCount, @Param("ca") String couponAmount, @Param("id") String id,
                           @Param("uid") String uid, @Param("op") int op, Callback<Model> callback);

    @GET(ApiConfig.URL_FAV)
    void reportFav(@Param("c") int channel, @Param("mid") String merchant, @Param("gid") String goodsId, @Param("gsku") String
            goodsSkuCode, @Param("gn") String goodsName, @Param("gi") String goodsImage, @Param("id") String id,
                          @Param("uid") String uid, @Param("op") int op, Callback<Model> callback);

    @GET(ApiConfig.URL_ORDER)
    void reportOrder(@Param("c") int channelId, @Param("mid") String merchant,@Param("on") String orderNumber,@Param("son")
    String subOrderNumber,@Param("os") String orderState,@Param("ou") String orderUser,@Param("op") String orderPrice,@Param("oa") String orderAddress,
                            @Param("cp") String couponPrice,@Param("fp") String freightPrice,@Param("pn") String goodsCount, @Param("opt") int op, Callback<Model> callback);

}
