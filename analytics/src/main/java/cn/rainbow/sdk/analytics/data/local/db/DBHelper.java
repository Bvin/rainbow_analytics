package cn.rainbow.sdk.analytics.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.rainbow.sdk.analytics.data.local.db.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.FavTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.THPageTable;

/**
 * Created by bvin on 2016/5/31.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FILE_NAME = "th_analytics";
    private static final int VERSION = 2;

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DB_FILE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTablesByV1(db);
        createTablesByV2(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            createTablesByV2(db);
        }
    }

    private void createTablesByV1(SQLiteDatabase db) {
        new EventTable(db).create();
        new AppTable(db).create();
        new PageTable(db).create();
        new CrashTable(db).create();
    }

    private void createTablesByV2(SQLiteDatabase db) {
        new THPageTable(db).create();
        new GoodsTable(db).create();
        new FavTable(db).create();
        new CartTable(db).create();
        new OrderTable(db).create();
    }
}
