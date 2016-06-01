package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.event.PageEvent;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageTable extends EventTable{

    public PageTable(TableCreator tableCreator, SQLiteDatabase database) {
        super(tableCreator, database);
    }

    public PageTable(SQLiteDatabase database) {
        this(new PageEvent(),database);
    }
}
