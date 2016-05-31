package cn.rainbow.sdk.analytics.data.local.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 32967 on 2016/5/31.
 */
public class SQLTable {

    private TableCreator mTableCreator;
    private SQLiteDatabase mDatabase;

    public SQLTable(TableCreator tableCreator, SQLiteDatabase database) {
        mTableCreator = tableCreator;
        mDatabase = database;
    }

    public SQLTable create() {
        mDatabase.execSQL(toCreateSql(mTableCreator));
        return this;
    }

    public SQLTable drop(){
        mDatabase.execSQL(toDropSql(mTableCreator));
        return this;
    }

    public SQLTable save() {
        mDatabase.insert(mTableCreator.tableName(), null, mTableCreator.values());
        return this;
    }

    private String toCreateSql(TableCreator tableCreator) {

        return "CREATE TABLE IF NOT EXISTS " + tableCreator.tableName() +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT," + tableCreator.defineFields()+")";

    }

    private String toDropSql(TableCreator tableCreator) {
        return "DROP TABLE IF EXISTS " + tableCreator.tableName();
    }
}
