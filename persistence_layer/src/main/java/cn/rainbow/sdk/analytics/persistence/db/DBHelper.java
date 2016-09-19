package cn.rainbow.sdk.analytics.persistence.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.rainbow.sdk.analytics.persistence.db.table.EventTable;

/**
 * Created by bvin on 2016/9/13.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_FILE_NAME = "rainbow_analytics";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableByV1(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableByV1(SQLiteDatabase db){
        db.execSQL(EventTable.toCreateSql());
    }
}
