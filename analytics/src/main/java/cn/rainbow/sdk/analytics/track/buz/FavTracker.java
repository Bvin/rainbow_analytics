package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.FavTable;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.FavReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class FavTracker extends AbsEventTracker<FavoriteEvent> {

    public static final int EVENT_ID = 1131;

    private FavoriteEvent mEvent;
    private FavTable mTable;

    public FavTracker(Context context) {
        super(context, EVENT_ID, "商品收藏统计");
    }

    public void startTrack(FavoriteEvent event){
        if (event == null) {
            throw new RuntimeException("event must not be null.");
        }
        mEvent = event;
        onEventStart();
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();
    }

    @Override
    protected void push() {
        new FavReporter(mEvent).push(listener());
    }

    @Override
    public FavoriteEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new FavTable(mContext);
        }
        return mTable;
    }

}
