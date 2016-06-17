package cn.rainbow.sdk.analytics.data.local.db.buz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.TableCreator;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;

/**
 * Created by bvin on 2016/6/17.
 */
public class THPageTable extends AbsEventTable<THPageEvent> implements TableCreator{

    private static final String NAME = "th_pages";

    public THPageTable(SQLiteDatabase database) {
        super(database);
        setTableCreator(this);
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
                ApvReporter.Keys.DEVICE_ID + " TEXT";
    }

    @Override
    protected THPageEvent take(Cursor cursor) {
        return new THPageEvent(cursor);
    }
}
