package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.CartReporter;

/**
 * Created by bvin on 2016/6/17.
 */
public class CartTable extends AbsEventTable<CartEvent> {

    public static final String TABLE_NAME = "carts";

    public CartTable(Context context) {
        super(context);
    }

    @Override
    protected CartEvent newEvent(Cursor cursor) {
        return new CartEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  CartReporter.Keys.CHANNEL_ID + " INT," +
                CartReporter.Keys.MERCHANT_ID + " TEXT," +
                CartReporter.Keys.OPERATION + " INT," +
                CartReporter.Keys.GOODS_ID + " TEXT," +
                CartReporter.Keys.GOODS_NAME + " TEXT," +
                CartReporter.Keys.GOODS_IMAGE + " TEXT," +
                CartReporter.Keys.GOODS_SKU_CODE + " TEXT," +
                CartReporter.Keys.GOODS_PRICE + " TEXT," +
                CartReporter.Keys.GOODS_SELL_PRICE + " TEXT," +
                CartReporter.Keys.GOODS_COUNT + " TEXT," +
                CartReporter.Keys.COUPON_AMOUNT + " TEXT," +
                CartReporter.Keys.DEVICE_ID + " TEXT," +
                CartReporter.Keys.USER_ID + " TEXT," +
                ApvReporter.Keys.TRACE_NUMBER + " TEXT";
    }

    @Override
    public SQLTable alter(SQLiteDatabase db) {
        db.execSQL(addColumn(ApvReporter.Keys.TRACE_NUMBER, "TEXT"));
        return this;
    }
}
