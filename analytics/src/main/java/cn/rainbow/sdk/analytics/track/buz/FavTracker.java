package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;

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
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        reportFav();
    }

    private void reportFav(){
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        httpLite.setBaseUrl(ApiConfig.HOST);
        Api api = httpLite.retrofit(Api.class, new PreRequestListener());
        api.reportFav(mEvent.getChannelId(),mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), urlEncode(mEvent.getGoodsName())
                , urlEncode(mEvent.getGoodsImage()), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation(),new BaseResponseCallback(this));
    }

    private String urlEncode(String content){
        try {
            return URLEncoder.encode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
    }

    @Override
    public void onFailed(Request request, Exception e) {
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
