package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 32967 on 2016/5/31.
 */
public class SQLTable {

    public static final String _ID = "_id";

    protected TableCreator mTableCreator;
    protected SQLiteDatabase mDatabase;

    public SQLTable(SQLiteDatabase database) {
        mDatabase = database;
    }

    public void setTableCreator(TableCreator tableCreator) {
        mTableCreator = tableCreator;
    }

    public SQLTable create() {
        mDatabase.execSQL(toCreateSql(mTableCreator));
        return this;
    }

    public SQLTable drop(){
        mDatabase.execSQL(toDropSql(mTableCreator));
        return this;
    }

    public SQLTable save(TableSave tableSave) {
        mDatabase.insert(mTableCreator.tableName(), null, tableSave.saveValues());
        return this;
    }

    private String toCreateSql(TableCreator tableCreator) {

        return "CREATE TABLE IF NOT EXISTS " + tableCreator.tableName() +
                "( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + tableCreator.tableColumns() + ")";

    }

    private String toDropSql(TableCreator tableCreator) {
        return "DROP TABLE IF EXISTS " + tableCreator.tableName();
    }
}
