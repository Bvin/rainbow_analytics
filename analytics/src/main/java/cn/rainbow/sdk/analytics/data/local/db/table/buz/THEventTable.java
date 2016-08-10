package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.event.buz.THEvent;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

/**
 * Created by bvin on 2016/8/10.
 */
public class THEventTable extends AbsEventTable<THEvent>{

    public static final String TABLE_NAME = "th_events";

    public THEventTable(Context context) {
        super(context);
    }

    @Override
    protected THEvent newEvent(Cursor cursor) {
        return new THEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  THEventReport.Keys.CHANNEL_ID + " INT," +
                THEventReport.Keys.MERCHANT_ID + " TEXT," +
                THEventReport.Keys.EVENT_ID + " TEXT," +
                THEventReport.Keys.PAGE + " TEXT," +
                THEventReport.Keys.LINK + " TEXT," +
                THEventReport.Keys.USER_ID + " TEXT," +
                THEventReport.Keys.DEVICE_ID + " TEXT," +
                THEventReport.Keys.TRACE_NUMBER + " TEXT";
    }
}
