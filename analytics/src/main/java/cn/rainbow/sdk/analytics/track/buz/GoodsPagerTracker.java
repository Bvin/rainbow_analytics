package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/13.
 */
public class GoodsPagerTracker extends THPageTracker {

    private GoodsViewEvent mEvent;
    private GoodsTable mTable;

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
    protected void push() {
        new GpvReporter(mEvent).push(this);
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
        if (mTable == null) {
            mTable = new GoodsTable(database);
        }
        return mTable;
    }
}
