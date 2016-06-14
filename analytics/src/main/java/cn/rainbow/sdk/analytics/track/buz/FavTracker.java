package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import alexclin.httplite.listener.RequestListener;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/14.
 */
public class FavTracker extends AbsEventTracker<FavoriteEvent> implements Callback<Model> {

    public static final int EVENT_ID = 1131;

    private FavoriteEvent mEvent;

    public FavTracker() {
        super(EVENT_ID, "商品收藏统计");
    }

    public void startTrack(Context context,FavoriteEvent event){
        attachContext(context);
        mEvent = event;
        reportFav();
    }

    private void reportFav(){
        /*Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportFav(mEvent.getChannelId(),mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getId(),
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
        api.reportFav(mEvent.getChannelId(),mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation(),this);
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
        if (model != null) {
            if (model.getRet() == 200) {
                Log.d("reportFav-response:", model.getMessage());
            }
        }
    }

    @Override
    public void onFailed(Request request, Exception e) {
        //失败应重传...
        Log.e("FavTracker", "onFailure: ", e);
    }

    @Override
    public FavoriteEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(FavoriteEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }

}
