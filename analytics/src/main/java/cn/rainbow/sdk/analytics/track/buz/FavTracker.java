package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.FavTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.FavReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class FavTracker extends AbsEventTracker<FavoriteEvent> implements Callback<Model> {

    public static final int EVENT_ID = 1131;

    private FavoriteEvent mEvent;
    private FavTable mTable;

    public FavTracker() {
        super(EVENT_ID, "商品收藏统计");
    }

    public void startTrack(Context context,FavoriteEvent event){
        attachContext(context);
        mEvent = event;
        onEventStart();
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();
    }

    @Override
    protected void push() {
        new FavReporter(mEvent).push(this);
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
        if (mTable == null) {
            mTable = new FavTable(database);
        }
        return mTable;
    }

}
