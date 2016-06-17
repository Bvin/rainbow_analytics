package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.CartTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.CartReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class CartTracker extends AbsEventTracker<CartEvent> implements alexclin.httplite.listener.Callback<Model> {

    public static final int EVENT_ID = 1040;

    private CartEvent mEvent;
    private CartTable mTable;

    public CartTracker() {
        super(EVENT_ID, "购物车统计");
    }

    public void startTrack(Context context,CartEvent event){
        attachContext(context);
        mEvent = event;
        onEventStart();
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();
    }

    @Override
    protected void push() {
        new CartReporter(mEvent).push(this);
    }

    @Override
    public CartEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(CartEvent event, SQLiteDatabase database) {
        if (mTable == null) {
            mTable = new CartTable(database);
        }
        return mTable;
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
    }

    @Override
    public void onFailed(Request request, Exception e) {
    }
}
