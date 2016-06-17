package cn.rainbow.sdk.analytics.data.local.db.buz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.TableCreator;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class GoodsTable extends AbsEventTable<GoodsViewEvent> implements TableCreator{

    private static final String TABLE_NAME = "goods";

    public GoodsTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    protected GoodsViewEvent take(Cursor cursor) {
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
                GpvReporter.Keys.GOODS_ID + " TEXT," +
                GpvReporter.Keys.GOODS_NAME + " TEXT," +
                GpvReporter.Keys.GOODS_IMAGE + " TEXT," +
                GpvReporter.Keys.ENTER_TIME + " TEXT," +
                GpvReporter.Keys.LEAVE_TIME + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY1 + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY2 + " TEXT," +
                GpvReporter.Keys.GOODS_CATEGORY3 + " TEXT," +
                GpvReporter.Keys.DEVICE_ID + " TEXT," +
                GpvReporter.Keys.USER_ID + " TEXT";
    }
}
