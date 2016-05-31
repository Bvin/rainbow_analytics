package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class EventTable extends SQLTable{

    public EventTable(TableCreator tableCreator, SQLiteDatabase database) {
        super(tableCreator, database);
    }

    public EventTable(SQLiteDatabase database) {
        super(new Event(), database);
    }

}
