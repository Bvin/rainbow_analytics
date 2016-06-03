package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.event.PageEvent;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageTable extends EventTable{

    public PageTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public String tableName() {
        return "pages";
    }

    @Override
    public String tableColumns() {
         return Columns.PAGE + " TEXT," +
                Columns.PREVIOUS_PAGE + " TEXT," +
                 EventTable.Columns.EVENT_START_DATE + " TEXT," +
                 EventTable.Columns.EVENT_END_DATE + " TEXT," +
                 EventTable.Columns.EVENT_DURATION + " LONG";
    }

    public class Columns{
        public static final String PAGE = "page";
        public static final String PREVIOUS_PAGE = "previous_page";
    }
}
