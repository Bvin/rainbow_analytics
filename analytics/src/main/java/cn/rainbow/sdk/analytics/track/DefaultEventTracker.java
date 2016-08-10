package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class DefaultEventTracker extends AbsEventTracker {

    private Event mEvent;
    private SQLTable mTable;

    public DefaultEventTracker(Context context,long eventId, String eventName) {
        super(context,eventId, eventName);
    }

    @Override
    public Event takeEvent() {
        if (mEvent == null) {
            mEvent = new Event(mEventId);
        }
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {

        if (mTable == null) {
            mTable = new EventTable(mContext);
        }
        return mTable;
    }

}
