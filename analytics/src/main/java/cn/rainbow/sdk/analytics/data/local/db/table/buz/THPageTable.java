package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.TableCreator;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;

/**
 * Created by bvin on 2016/6/17.
 */
public class THPageTable extends AbsEventTable<THPageEvent>{

    public static final String NAME = "th_pages";

    public THPageTable(Context context) {
        super(context);
    }

    @Override
    public String tableName() {
        return NAME;
    }

    @Override
    public String tableColumns() {
        return  ApvReporter.Keys.CHANNEL_ID + " INT," +
                ApvReporter.Keys.MERCHANT_ID + " TEXT," +
                ApvReporter.Keys.PAGE + " TEXT," +
                ApvReporter.Keys.APP_VERSION + " TEXT," +
                ApvReporter.Keys.ENTER_TIME + " TEXT," +
                ApvReporter.Keys.LEAVE_TIME + " TEXT," +
                ApvReporter.Keys.MOBILE + " TEXT," +
                ApvReporter.Keys.OS + " TEXT," +
                ApvReporter.Keys.OS_VERSION + " TEXT," +
                ApvReporter.Keys.DEVICE_ID + " TEXT," +
                ApvReporter.Keys.TRACE_NUMBER + " TEXT";
    }

    @Override
    public SQLTable alter(SQLiteDatabase db) {
        db.execSQL(addColumn(ApvReporter.Keys.TRACE_NUMBER, "TEXT"));
        return this;
    }

    @Override
    protected THPageEvent newEvent(Cursor cursor) {
        return new THPageEvent(cursor);
    }
}