package cn.rainbow.sdk.analytics.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.FavTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THPageTable;
import cn.rainbow.sdk.analytics.data.local.db.table.AppTable;
import cn.rainbow.sdk.analytics.data.local.db.table.CrashTable;
import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.PageTable;

/**
 * Created by bvin on 2016/5/31.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FILE_NAME = "th_analytics";
    private static final int VERSION = 3;

    private Context mContext;

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DB_FILE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTablesByV1(db);
        createTablesByV2(db);
        createTablesByV3(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            createTablesByV2(db);
        }else if(oldVersion == 2 && newVersion == 3){
            createTablesByV3(db);
            alterTablesByV3(db);
        }
    }

    //v1基础通用事件
    private void createTablesByV1(SQLiteDatabase db) {
        new EventTable(mContext).create(db);
        new AppTable(mContext).create(db);
        new PageTable(mContext).create(db);
        new CrashTable(mContext).create(db);
    }

    //v2业务事件
    private void createTablesByV2(SQLiteDatabase db) {
        new THPageTable(mContext).create(db);
        new GoodsTable(mContext).create(db);
        new FavTable(mContext).create(db);
        new CartTable(mContext).create(db);
        new OrderTable(mContext).create(db);
    }

    //v3创建THEventTable
    private void createTablesByV3(SQLiteDatabase db) {
        new THEventTable(mContext).create(db);
    }

    //v2的表添加字段
    private void alterTablesByV3(SQLiteDatabase db) {
        new THPageTable(mContext).alter(db);
        new GoodsTable(mContext).alter(db);
        new FavTable(mContext).alter(db);
        new CartTable(mContext).alter(db);
        new OrderTable(mContext).alter(db);
    }
}
