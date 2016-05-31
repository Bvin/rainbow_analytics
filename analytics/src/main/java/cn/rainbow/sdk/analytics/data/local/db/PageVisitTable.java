package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageVisitTable extends EventTable{

    public PageVisitTable(TableCreator tableCreator, SQLiteDatabase database) {
        super(tableCreator, database);
    }

    public PageVisitTable(SQLiteDatabase database) {
        super(database);
    }
}
