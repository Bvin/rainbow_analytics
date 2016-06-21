package cn.rainbow.sdk.analytics.data.local.db.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class EventTable extends AbsEventTable<Event> {

    public static final String TABLE_NAME = "event";

    public EventTable(Context context) {
        super(context);
    }

    @Override
    protected Event newEvent(Cursor cursor) {
        return new Event(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return Columns.EVENT_ID + " LONG," +
                Columns.EVENT_NAME + " TEXT," +
                Columns.EVENT_DESC + " TEXT," +
                Columns.EVENT_TYPE + " INT," +
                Columns.EVENT_START_DATE + " TEXT," +
                Columns.EVENT_END_DATE + " TEXT," +
                Columns.EVENT_DURATION + " LONG";
    }

    public class Columns{
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_NAME = "event_name";
        public static final String EVENT_DESC = "event_desc";
        public static final String EVENT_TYPE = "event_type";
        public static final String EVENT_START_DATE = "start_date";
        public static final String EVENT_END_DATE = "end_date";
        public static final String EVENT_DURATION = "duration";
    }
}
