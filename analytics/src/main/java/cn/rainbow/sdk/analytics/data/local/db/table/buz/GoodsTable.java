package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class GoodsTable extends AbsEventTable<GoodsViewEvent>{

    public static final String TABLE_NAME = "goods";

    public GoodsTable(Context context) {
        super(context);
    }

    @Override
    protected GoodsViewEvent newEvent(Cursor cursor) {
        return new GoodsViewEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  GpvReporter.Keys.CHANNEL_ID + " INT," +
                GpvReporter.Keys.MERCHANT_ID + " TEXT," +
                Columns.GOODS_ID + " TEXT," +
                GpvReporter.Keys.GOODS_NAME + " TEXT," +
                Columns.GOODS_IMAGE + " TEXT," +
                GpvReporter.Keys.ENTER_TIME + " TEXT," +
                GpvReporter.Keys.LEAVE_TIME + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY1 + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY2 + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY3 + " TEXT," +
                GpvReporter.Keys.DEVICE_ID + " TEXT," +
                GpvReporter.Keys.USER_ID + " TEXT," +
                ApvReporter.Keys.TRACE_NUMBER + " TEXT";
    }

    @Override
    public SQLTable alter(SQLiteDatabase db) {
        db.execSQL(addColumn(ApvReporter.Keys.TRACE_NUMBER, "TEXT"));
        return this;
    }

    public class Columns{
        public static final String GOODS_ID = "gi";
        public static final String GOODS_IMAGE = "gm";
    }
}
