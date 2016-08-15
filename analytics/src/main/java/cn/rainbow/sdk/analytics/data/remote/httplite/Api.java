package cn.rainbow.sdk.analytics.data.remote.httplite;

import alexclin.httplite.annotation.GET;
import alexclin.httplite.annotation.Param;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.FavReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

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
    void reportAPV(@Param("type") String reportType,
                   @Param(ApvReporter.Keys.CHANNEL_ID) int channel,
                   @Param(ApvReporter.Keys.MERCHANT_ID) String merchant,
                   @Param(ApvReporter.Keys.PAGE) String url,
                   @Param(ApvReporter.Keys.APP_VERSION) String version,
                   @Param(ApvReporter.Keys.ENTER_TIME) String enterTime,
                   @Param(ApvReporter.Keys.LEAVE_TIME) String leavingTime,
                   @Param(ApvReporter.Keys.MOBILE) String device,
                   @Param(ApvReporter.Keys.OS) String os,
                   @Param(ApvReporter.Keys.OS_VERSION) String osVersion,
                   @Param(ApvReporter.Keys.DEVICE_ID) String id,
                   @Param(ApvReporter.Keys.TRACE_NUMBER) String tn,
                   Callback<Model> callback);


    @GET(ApiConfig.URL_GOODS_PV)
    void reportGPV(@Param("type") String reportType,
                   @Param(GpvReporter.Keys.CHANNEL_ID) int channel,
                   @Param(GpvReporter.Keys.MERCHANT_ID) String merchant,
                   @Param(GpvReporter.Keys.GOODS_ID) String goodsId,
                   @Param(GpvReporter.Keys.GOODS_NAME) String goodsName,
                   @Param(GpvReporter.Keys.GOODS_IMAGE) String goodsImage,
                   @Param(GpvReporter.Keys.ENTER_TIME) String enterTime,
                   @Param(GpvReporter.Keys.LEAVE_TIME) String leavingTime,
                   @Param(GpvReporter.Keys.GOODS_CATEGORY1) String goodsCategory1,
                   @Param(GpvReporter.Keys.GOODS_CATEGORY2) String goodsCategory2,
                   @Param(GpvReporter.Keys.GOODS_CATEGORY3) String goodsCategory3,
                   @Param(GpvReporter.Keys.DEVICE_ID) String id,
                   @Param(GpvReporter.Keys.USER_ID) String uid,
                   @Param(ApvReporter.Keys.TRACE_NUMBER) String tn,
                   Callback<Model> callback);

    @GET(ApiConfig.URL_CART)
    void reportCart(@Param("type") String reportType,
                    @Param("c") int channel,
                    @Param("mid") String merchant,
                    @Param("gid") String goodsId,
                    @Param("gsku") String goodsSkuCode,
                    @Param("gn") String goodsName,
                    @Param("gi") String goodsImage,
                    @Param("gp") String goodsPrice,
                    @Param("ga") String goodsSellPrice,
                    @Param("gc") String goodsCount,
                    @Param("ca") String couponAmount,
                    @Param("id") String id,
                    @Param("uid") String uid,
                    @Param("op") int op,
                    @Param(ApvReporter.Keys.TRACE_NUMBER) String tn,
                    Callback<Model> callback);

    @GET(ApiConfig.URL_FAV)
    void reportFav(@Param("type") String reportType,
                   @Param(FavReporter.Keys.CHANNEL_ID) int channel,
                   @Param(FavReporter.Keys.MERCHANT_ID) String merchant,
                   @Param(FavReporter.Keys.GOODS_ID) String goodsId,
                   @Param(FavReporter.Keys.GOODS_SKU_CODE) String goodsSkuCode,
                   @Param(FavReporter.Keys.GOODS_NAME) String goodsName,
                   @Param(FavReporter.Keys.GOODS_IMAGE) String goodsImage,
                   @Param(FavReporter.Keys.DEVICE_ID) String id,
                   @Param(FavReporter.Keys.USER_ID) String uid,
                   @Param(FavReporter.Keys.OPERATION) int op,
                   @Param(ApvReporter.Keys.TRACE_NUMBER) String tn,
                   Callback<Model> callback);

   @GET(ApiConfig.URL_EVENT)
   void reportEvent(@Param("type") String reportType,
                    @Param(THEventReport.Keys.CHANNEL_ID) int channel,
                    @Param(THEventReport.Keys.MERCHANT_ID) String merchant,
                    @Param(THEventReport.Keys.EVENT_ID) String eventId,
                    @Param(THEventReport.Keys.PAGE) String url,
                    @Param(THEventReport.Keys.LINK) String link,
                    @Param(THEventReport.Keys.DEVICE_ID) String id,
                    @Param(THEventReport.Keys.USER_ID) String uid,
                    @Param(THEventReport.Keys.TRACE_NUMBER) String tn,
                    @Param(THEventReport.Keys.ELEMENT_TRACE_NUMBER) String etn,
                    Callback<Model> callback);

}
