package cn.rainbow.sdk.analytics.data.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class EventTable extends SQLTable implements TableCreator{

    public static final String TABLE_NAME = "event";

    public EventTable(SQLiteDatabase database) {
        super(database);
        setTableCreator(this);
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

    public List<Event> query() {
        Cursor cursor = queryFull();
        if (cursor != null && cursor.moveToFirst()) {
            List<Event> eventList = new ArrayList<>();
            do {
                long eventId = cursor.getLong(cursor.getColumnIndex(Columns.EVENT_ID));
                String eventName = cursor.getString(cursor.getColumnIndex(Columns.EVENT_NAME));
                String eventDesc = cursor.getString(cursor.getColumnIndex(Columns.EVENT_DESC));
                int eventType = cursor.getInt(cursor.getColumnIndex(Columns.EVENT_TYPE));
                String eventStartDate = cursor.getString(cursor.getColumnIndex(Columns.EVENT_START_DATE));
                String eventEndDate = cursor.getString(cursor.getColumnIndex(Columns.EVENT_END_DATE));
                long eventDuration = cursor.getLong(cursor.getColumnIndex(Columns.EVENT_DURATION));
                Event event = new Event(eventId, eventName, eventDesc);
                event.setEventType(eventType);
                event.setStartDate(eventStartDate);
                event.setEndDate(eventEndDate);
                event.setDuration(eventDuration);
                eventList.add(event);
            } while (cursor.moveToNext());
            return eventList;
        }
        return null;
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
