package cn.rainbow.sdk.analytics.data.local.db.table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.event.AppEvent;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/1.
 */
public class AppTable extends EventTable {

    public static final String TABLE_NAME = "app";

    private Uri mCurTableUri;

    public AppTable(Context context) {
        super(context);
    }

    @Override
    protected Event newEvent(Cursor cursor) {
        // TODO: 2016/6/21 new AppEvent(cursor)
        return super.newEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  Columns.APP_ID + " TEXT," +
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

    //插入
    public void insert(AppEvent appEvent) {
        mCurTableUri = save(appEvent);//course save() means insert.
    }

    //更新
    public void update(AppEvent appEvent) {
        update(mCurTableUri, appEvent);
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
