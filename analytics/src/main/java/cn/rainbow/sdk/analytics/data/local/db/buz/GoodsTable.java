package cn.rainbow.sdk.analytics.data.local.db.buz;

import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.EventTable;

/**
 * Created by bvin on 2016/6/14.
 */
public class GoodsTable extends EventTable{

    public GoodsTable(SQLiteDatabase database) {
        super(database);
    }

    public class Keys {
        public static final String GOODS_ID = "gi";
        public static final String GOODS_SKU_CODE = "gs";
        public static final String GOODS_COUNT = "gc";
        public static final String GOODS_NAME = "gn";
        public static final String GOODS_IMAGE = "gm";
    }

}
