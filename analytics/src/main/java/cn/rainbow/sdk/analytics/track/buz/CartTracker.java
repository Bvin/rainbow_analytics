package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.CartReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class CartTracker extends AbsEventTracker<CartEvent> {

    public static final int EVENT_ID = 1040;

    private CartEvent mEvent;
    private CartTable mTable;

    public CartTracker(Context context) {
        super(context,EVENT_ID, "购物车统计");
    }

    public void startTrack(CartEvent event){
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
        new CartReporter(mEvent).push(this);
    }

    @Override
    public CartEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new CartTable(mContext);
        }
        return mTable;
    }
}
