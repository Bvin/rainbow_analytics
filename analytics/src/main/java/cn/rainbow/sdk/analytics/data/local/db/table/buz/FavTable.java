package cn.rainbow.sdk.analytics.data.local.db.table.buz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.TableCreator;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.FavReporter;

/**
 * Created by bvin on 2016/6/17.
 */
public class FavTable extends AbsEventTable<FavoriteEvent>{

    public static final String TABLE_NAME = "favs";

    public FavTable(Context context) {
        super(context);
    }

    @Override
    protected FavoriteEvent newEvent(Cursor cursor) {
        return new FavoriteEvent(cursor);
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String tableColumns() {
        return  FavReporter.Keys.CHANNEL_ID + " INT," +
                FavReporter.Keys.MERCHANT_ID + " TEXT," +
                FavReporter.Keys.OPERATION + " INT," +
                FavReporter.Keys.GOODS_ID + " TEXT," +
                FavReporter.Keys.GOODS_NAME + " TEXT," +
                FavReporter.Keys.GOODS_IMAGE + " TEXT," +
                FavReporter.Keys.GOODS_SKU_CODE + " TEXT," +
                FavReporter.Keys.DEVICE_ID + " TEXT," +
                FavReporter.Keys.USER_ID + " TEXT," +
                ApvReporter.Keys.TRACE_NUMBER + " TEXT";
    }

    @Override
    public SQLTable alter(SQLiteDatabase db) {
        db.execSQL(addColumn(ApvReporter.Keys.TRACE_NUMBER, "TEXT"));
        return this;
    }
}
