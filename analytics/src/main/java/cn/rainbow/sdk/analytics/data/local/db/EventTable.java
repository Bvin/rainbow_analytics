package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class EventTable extends SQLTable implements TableCreator{

    public EventTable(SQLiteDatabase database) {
        super(database);
        setTableCreator(this);
    }

    @Override
    public String tableName() {
        return "event";
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
