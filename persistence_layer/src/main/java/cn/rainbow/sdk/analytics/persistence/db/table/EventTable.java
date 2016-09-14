package cn.rainbow.sdk.analytics.persistence.db.table;

import android.provider.BaseColumns;

/**
 * Created by bvin on 2016/9/13.
 */
public class EventTable {

    public static final String TABLE_NAME = "events";

    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_EVENT = 1;

    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_ID = BaseColumns._ID;

    //表创建语句
    public static String toCreateSql() {

        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EVENT +" TEXT"+ ")";

    }

}
