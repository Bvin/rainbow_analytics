package cn.rainbow.sdk.analytics.track;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class DefaultEventTracker extends EventTracker{

    public DefaultEventTracker(long eventId, String eventName) {
        super(eventId, eventName);
    }

    @Override
    public Event createEvent() {
        return new Event(mEventId);
    }

    @Override
    public SQLTable createTable(Event event, SQLiteDatabase database) {
        return new EventTable(event, database);
    }

}
