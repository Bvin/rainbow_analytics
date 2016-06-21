package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
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
