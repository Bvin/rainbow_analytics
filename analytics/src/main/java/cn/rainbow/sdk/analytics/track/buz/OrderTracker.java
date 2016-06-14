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
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/14.
 */
public class OrderTracker extends AbsEventTracker<OrderEvent> implements alexclin.httplite.listener.Callback<Model> {

    public static final int EVENT_ID = 1122;

    private OrderEvent mEvent;

    public OrderTracker() {
        super(EVENT_ID, "订单统计");
    }

    public void startTrack(Context context,OrderEvent event){
        attachContext(context);
        mEvent = event;
        reportOrder();
    }

    private void reportOrder(){
        /*Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportOrder(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getOrderNumber(), mEvent.getSubOrderNumber(), mEvent.getOrderState()
                , mEvent.getOrderUser(), mEvent.getOrderPrice(), mEvent.getOrderAddress(), mEvent.getCouponPrice(), mEvent.getFreightPrice(), mEvent.getGoodsCount(),
                 mEvent.getOperation());
        call.enqueue(this);*/
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        Api api = httpLite.retrofit(Api.class, new RequestListener() {
            @Override
            public void onRequest(HttpLite lite, Request request, Type resultType) {
                Log.d("onRequest",request.toString());
            }
        });
        api.reportOrder(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getOrderNumber(), mEvent.getSubOrderNumber(), mEvent.getOrderState()
                , mEvent.getOrderUser(), mEvent.getOrderPrice(), mEvent.getOrderAddress(), mEvent.getCouponPrice(), mEvent.getFreightPrice(), mEvent.getGoodsCount(),
                mEvent.getOperation(),this);
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
        if (model != null) {
            if (model.getRet() == 200) {
                Log.d("reportOrder-response:", model.getMessage());
            }
        }
    }

    @Override
    public void onFailed(Request request, Exception e) {
        //失败应重传...
        Log.e("OrderTracker", "onFailure: ", e);
    }

    @Override
    public OrderEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(OrderEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }

}
