package cn.rainbow.sdk.analytics.track;

import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;

/**
 * Created by bvin on 2016/6/12.
 */
public class MarketingTracker implements Api{

    Api mApi;

    public MarketingTracker() {
        Retrofit retrofit = RetrofitClient.getInstance();
        mApi = retrofit.create(Api.class);
    }


    @Override
    public Call<Model> reportAPV(@Field("c") String channel, @Field("mid") String merchant, @Field("u") String url, @Field("v") String version, @Field("et") String enterTime, @Field("lt") String leavingTime, @Field("mb") String device, @Field("o") String os, @Field("ov") String osVersion, @Field("id") String id) {
        return mApi.reportAPV(channel, merchant, url, version, enterTime, leavingTime, device, os, osVersion, id);
    }

    @Override
    public Call<Model> reportGPV(@Field("c") String channel, @Field("mid") String merchant, @Field("gid") String goodsId, @Field("gn") String goodsName, @Field("gi") String goodsImage, @Field("et") String enterTime, @Field("lt") String leavingTime, @Field("gc1") String goodsCategory1, @Field("gc1") String goodsCategory2, @Field("gc1") String goodsCategory3, @Field("id") String id, @Field("uid") String uid) {
        return mApi.reportGPV(channel, merchant, goodsId, goodsName, goodsImage, enterTime, leavingTime, goodsCategory1, goodsCategory2, goodsCategory3, id, uid);
    }
}
