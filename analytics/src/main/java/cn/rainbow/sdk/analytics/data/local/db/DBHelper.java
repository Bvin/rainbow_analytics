package cn.rainbow.sdk.analytics.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bvin on 2016/5/31.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_FILE_NAME = "th_analytics";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DB_FILE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new EventTable(db).create();
        new AppTable(db).create();
        new PageTable(db).create();
        new CrashTable(db).create();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
    }
}
