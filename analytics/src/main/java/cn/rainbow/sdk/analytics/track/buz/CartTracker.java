package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.listener.RequestListener;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/14.
 */
public class CartTracker extends AbsEventTracker<CartEvent> implements alexclin.httplite.listener.Callback<Model> {

    public static final int EVENT_ID = 1040;

    private CartEvent mEvent;

    public CartTracker() {
        super(EVENT_ID, "购物车统计");
    }

    public void startTrack(Context context,CartEvent event){
        attachContext(context);
        mEvent = event;
        reportCart();
    }

    private void reportCart() {
        /*Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportCart(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getGoodsPrice(), mEvent.getGoodsSellPrice(), mEvent.getGoodsCount(), mEvent.getCouponAmount(), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation());
        call.enqueue(this);*/
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        Api api = httpLite.retrofit(Api.class, new RequestListener() {
            @Override
            public void onRequest(HttpLite lite, Request request, Type resultType) {
                Log.d("onRequest",request.toString());
            }
        });
        api.reportCart(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getGoodsPrice(), mEvent.getGoodsSellPrice(), mEvent.getGoodsCount(), mEvent.getCouponAmount(), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation(),this);
    }

    @Override
    public CartEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(CartEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
        if (model != null) {
            if (model.getRet() == 200) {
                Log.d("reportCart-response:", model.getMessage());
            }
        }
    }

    @Override
    public void onFailed(Request request, Exception e) {
        //失败应重传...
        Log.e("CartTracker", "onFailure: ", e);
    }
}
