package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;

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
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        httpLite.setBaseUrl(ApiConfig.HOST);
        StringBuilder sb = new StringBuilder(ApiConfig.URL_ORDER);
        sb.append("?");
        sb.append(mEvent.toString());
        for (OrderEvent.Goods goods : mEvent.getGoodsList()) {
            sb.append(goods.toString());
        }
        if (sb.toString().endsWith("&")) {
            sb.delete(sb.toString().length() - 1, sb.toString().length());
        }
        if (!TextUtils.isEmpty(sb.toString()))
            httpLite.url(sb.toString()).get().async(new BaseResponseCallback(this,true));
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
    }

    @Override
    public void onFailed(Request request, Exception e) {
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
