package cn.rainbow.sdk.analytics.data.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.event.AppEvent;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/1.
 */
public class AppTable extends EventTable{


    public AppTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public String tableName() {
        return "app";
    }

    @Override
    public String tableColumns() {
        return Columns.APP_ID + " TEXT," +
                Columns.APP_NAME + " TEXT," +
                Columns.APP_VERSION + " INT," +
                Columns.APP_VERSION_NAME + " TEXT," +
                Columns.SDK_MIN + " INT," +
                Columns.SDK_TARGET + " INT," +
                Columns.BUILD_TYPE + " TEXT," +
                Columns.BUILD_VARIANT + " TEXT," +
                Columns.SYSTEM_VERSION + " INT," +
                Columns.DEVICE_MODEL + " TEXT," +
                Columns.SCREEN_SIZE + " TEXT," +
                EventTable.Columns.EVENT_START_DATE + " TEXT," +
                EventTable.Columns.EVENT_END_DATE + " TEXT," +
                EventTable.Columns.EVENT_DURATION + " LONG";
    }

    public void insert(AppEvent appEvent){
        save(appEvent);//course save() means insert.
    }

    public void update(AppEvent appEvent) {
        Cursor cursor = mDatabase.rawQuery("select * from " + mTableCreator.tableName() + " order by " + SQLTable._ID + " desc limit 0,1", null);
        if (cursor.moveToFirst()) {
            String endDate = cursor.getString(cursor.getColumnIndex(EventTable.Columns.EVENT_END_DATE));
            long duration = cursor.getLong(cursor.getColumnIndex(EventTable.Columns.EVENT_DURATION));
            if (TextUtils.isEmpty(endDate) || duration == 0) {
                int _id = cursor.getInt(cursor.getColumnIndex(SQLTable._ID));
                mDatabase.update(mTableCreator.tableName(), appEvent.saveValues(),
                        SQLTable._ID + "=?", new String[]{String.valueOf(_id)});
            }else {
                //如果有应该新插入一条..
            }
        }
        cursor.close();
    }

    public class Columns {
        public static final String APP_ID = "app_id";
        public static final String APP_NAME = "app_name";
        public static final String APP_VERSION = "app_version";
        public static final String APP_VERSION_NAME = "app_version_name";
        public static final String SDK_MIN = "sdk_min";
        public static final String SDK_TARGET = "sdk_target";
        public static final String BUILD_TYPE = "build_type";
        public static final String BUILD_VARIANT = "build_variant";
        public static final String SYSTEM_VERSION = "system_version";
        public static final String DEVICE_MODEL = "device_model";
        public static final String SCREEN_SIZE = "screen_size";
    }
}
