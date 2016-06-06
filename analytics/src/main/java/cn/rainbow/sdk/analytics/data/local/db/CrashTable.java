package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bvin on 2016/6/6.
 */
public class CrashTable extends EventTable{

    public CrashTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public String tableName() {
        return "crash";
    }

    @Override
    public String tableColumns() {
        return  Columns.CRASH_INFO +" TEXT," +
                AppTable.Columns.APP_VERSION + " INT," +
                AppTable.Columns.APP_VERSION_NAME + " TEXT," +
                AppTable.Columns.SDK_MIN + " INT," +
                AppTable.Columns.SDK_TARGET + " INT," +
                AppTable.Columns.BUILD_TYPE + " TEXT," +
                AppTable.Columns.BUILD_VARIANT + " TEXT," +
                AppTable.Columns.SYSTEM_VERSION + " INT," +
                AppTable.Columns.DEVICE_MODEL + " TEXT," +
                AppTable.Columns.SCREEN_SIZE + " TEXT," +
                Columns.USER_ID + " TEXT," +
                Columns.NET_ENV + " TEXT," +
                EventTable.Columns.EVENT_START_DATE + " TEXT," +
                EventTable.Columns.EVENT_END_DATE + " TEXT," +
                EventTable.Columns.EVENT_DURATION + " LONG";
    }

    public class Columns{
        public static final String CRASH_INFO = "crash_info";
        public static final String USER_ID = "user_id";
        public static final String NET_ENV = "net_env";
    }

}
