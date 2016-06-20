package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.OrderReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class OrderTracker extends AbsEventTracker<OrderEvent> {

    public static final int EVENT_ID = 1122;

    private OrderEvent mEvent;
    private OrderTable mTable;

    public OrderTracker() {
        super(EVENT_ID, "订单统计");
    }

    public void startTrack(Context context,OrderEvent event){
        attachContext(context);
        mEvent = event;
        onEventStart();//一定要调用，否则后面会传不到Event
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();//一定要调用，否则不会保存数据库，也不会推送
    }

    @Override
    protected void push() {
       new OrderReporter(mEvent).push(this);
    }

    @Override
    public OrderEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(OrderEvent event, SQLiteDatabase database) {
        if (mTable == null) {
            mTable = new OrderTable(database);
        }
        return mTable;
    }

}
