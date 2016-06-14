package cn.rainbow.sdk.analytics.data.local.db.buz;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.TableSave;

/**
 * Created by bvin on 2016/6/14.
 */
public class OrderTable extends EventTable{

    public OrderTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public String tableName() {
        return super.tableName();
    }

    @Override
    public String tableColumns() {
        return super.tableColumns();
    }

    public class Keys {
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String ORDER_NUMBER = "on";
        public static final String SUB_ORDER_NUMBER = "son";
        public static final String ORDER_STATE = "os";
        public static final String ORDER_USER = "ou";
        public static final String ORDER_PRICE = "op";
        public static final String ORDER_ADDRESS = "oa";
        public static final String COUPON_PRICE = "cp";
        public static final String FREIGHT_PRICE = "fp";
        public static final String GOODS_COUNT = "pn";
        public static final String OPERATION_TYPE = "opt";
    }
}
