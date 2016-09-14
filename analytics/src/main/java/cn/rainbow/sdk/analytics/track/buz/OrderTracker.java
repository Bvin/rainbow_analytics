package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
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

    public OrderTracker(Context context) {
        super(context, EVENT_ID, "订单统计");
    }

    public void startTrack(OrderEvent event){
        mEvent = event;
        onEventStart();//一定要调用，否则后面会传不到Event
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();//一定要调用，否则不会保存数据库，也不会推送
    }

    @Override
    protected void push() {
       new OrderReporter(mEvent).push(listener());
    }

    @Override
    public OrderEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new OrderTable(mContext);
        }
        return mTable;
    }

}
