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
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/13.
 */
public class GoodsPagerTracker extends THPageTracker {

    private GoodsViewEvent mEvent;

    public GoodsPagerTracker(Context context) {
        super(context);
    }

    @Override
    public void onPageStartAfter(String previousPage) {
        onEventStart();
    }

    public void startGoodsPage(GoodsViewEvent eventData){
        mEvent = eventData;
        onPageStartAfter(null);
    }

    @Override
    public void onPageEnd() {
        onEventEnd();
        reportGPV();
    }

    private void reportGPV() {
        /*Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportGPV(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsName(), mEvent.getGoodsImage(),
                mEvent.getStartDate(), mEvent.getEndDate(), mEvent.getCategory1(), mEvent.getCategory2(), mEvent.getCategory3(), mEvent.getId()
                , mEvent.getUid());
        call.enqueue(this);*/
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        Api api = httpLite.retrofit(Api.class, new RequestListener() {
            @Override
            public void onRequest(HttpLite lite, Request request, Type resultType) {
                Log.d("onRequest",request.toString());
            }
        });
        api.reportGPV(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsName(), mEvent.getGoodsImage(),
                mEvent.getStartDate(), mEvent.getEndDate(), mEvent.getCategory1(), mEvent.getCategory2(), mEvent.getCategory3(), mEvent.getId()
                , mEvent.getUid(),this);
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
        if (model != null) {
            if (model.getRet() == 200) {
                Log.d("reportGPV-response:", model.getMessage());
            }
        }
    }

    @Override
    public void onFailed(Request request, Exception e) {
        //失败应重传...
        Log.e("GoodsPagerTracker", "onFailure: ", e);
    }

    @Override
    public PageEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(PageEvent event, SQLiteDatabase database) {
        return super.createTable(event, database);
    }
}
