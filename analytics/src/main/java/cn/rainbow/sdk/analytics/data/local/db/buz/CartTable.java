package cn.rainbow.sdk.analytics.data.local.db.buz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.TableCreator;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.report.CartReporter;

/**
 * Created by bvin on 2016/6/17.
 */
public class CartTable extends AbsEventTable<CartEvent> implements TableCreator{

    private static final String TABLE_NAME = "carts";

    public CartTable(SQLiteDatabase database) {
        super(database);
        setTableCreator(this);
    }

    @Override
    protected CartEvent take(Cursor cursor) {
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
                CartReporter.Keys.USER_ID + " TEXT";
    }
}
