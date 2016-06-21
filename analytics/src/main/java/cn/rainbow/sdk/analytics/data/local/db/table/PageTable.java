package cn.rainbow.sdk.analytics.data.local.db.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageTable extends EventTable {

    public static final String TABLE_NAME = "pages";

    public PageTable(Context context) {
        super(context);
    }

    @Override
    protected Event newEvent(Cursor cursor) {
        // TODO: 2016/6/21 new PageEvent(cursor)
        return super.newEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
         return  Columns.PAGE + " TEXT," +
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
