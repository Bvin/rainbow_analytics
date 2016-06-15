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
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;

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
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onPageStartAfter(null);
    }

    @Override
    public void onPageEnd() {
        onEventEnd();
        reportGPV();
    }

    private void reportGPV() {
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        httpLite.setBaseUrl(ApiConfig.HOST);
        Api api = httpLite.retrofit(Api.class, new PreRequestListener());
        api.reportGPV(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), urlEncode(mEvent.getGoodsName()), urlEncode(mEvent.getGoodsImage()),
                mEvent.getStartDate(), mEvent.getEndDate(), mEvent.getCategory1(), mEvent.getCategory2(), mEvent.getCategory3(), mEvent.getId()
                , mEvent.getUid(),new BaseResponseCallback(this));
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
    public PageEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(PageEvent event, SQLiteDatabase database) {
        return super.createTable(event, database);
    }
}
