package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.OrderReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class OrderTable extends AbsEventTable<OrderEvent>{

    public static final String TABLE_NAME = "orders";

    public OrderTable(Context context) {
        super(context);
    }

    @Override
    protected OrderEvent newEvent(Cursor cursor) {
        return new OrderEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  OrderReporter.Keys.CHANNEL_ID + " INT," +
                OrderReporter.Keys.MERCHANT_ID + " TEXT," +
                OrderReporter.Keys.OPERATION_TYPE + " INT," +
                Columns.ORDER_NUMBER + " TEXT," +
                OrderReporter.Keys.SUB_ORDER_NUMBER + " TEXT," +
                OrderReporter.Keys.ORDER_STATE + " TEXT," +
                OrderReporter.Keys.ORDER_USER + " TEXT," +
                OrderReporter.Keys.ORDER_PRICE + " TEXT," +
                OrderReporter.Keys.ORDER_ADDRESS + " TEXT," +
                OrderReporter.Keys.COUPON_PRICE + " TEXT," +
                OrderReporter.Keys.FREIGHT_PRICE + " TEXT," +
                OrderReporter.Keys.GOODS_TOTAL + " TEXT," +
                Columns.GOODS_LIST + " TEXT," +
                ApvReporter.Keys.TRACE_NUMBER + " TEXT";
    }


    @Override
    public SQLTable alter(SQLiteDatabase db) {
        db.execSQL(addColumn(ApvReporter.Keys.TRACE_NUMBER, "TEXT"));
        return this;
    }

    public class Columns{
        public static final String ORDER_NUMBER = "order_number";
        public static final String GOODS_LIST = "i";
    }
}
