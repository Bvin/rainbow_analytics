package cn.rainbow.sdk.analytics.data.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/17.
 */
public abstract class AbsEventTable<T extends Event> extends SQLTable{

    public AbsEventTable(SQLiteDatabase database) {
        super(database);
    }

    public List<T> query() {
        Cursor cursor = queryFull();
        if (cursor != null && cursor.moveToFirst()) {
            List<T> eventList = new ArrayList<>();
            do {
                eventList.add(take(cursor));
            } while (cursor.moveToNext());
            return eventList;
        }
        return null;
    }

    protected abstract T take(Cursor cursor);
}
