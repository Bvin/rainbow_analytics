package cn.rainbow.sdk.analytics.data.local.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.DBProvider;

/**
 * Created by 32967 on 2016/5/31.
 * <p>确保所有操作都在setTableCreator后
 */
public class SQLTable {

    public static final String _ID = BaseColumns._ID;

    protected TableCreator mTableCreator;
    protected Context mContext;

    /**
     * SQLTable.
     * @param context 上下文，需要操作数据
     */
    public SQLTable(Context context) {
        mContext = context;
    }

    protected void setTableCreator(TableCreator tableCreator) {
        mTableCreator = tableCreator;
    }

    /**
     * 建表专用
     * @param db 数据库(不做任何操作)
     * @return
     */
    public SQLTable create(SQLiteDatabase db) {
        db.execSQL(toCreateSql(mTableCreator));
        return this;
    }

    /**
     * 删表专用
     * @param db 数据库(不做任何操作)
     * @return
     */
    public SQLTable drop(SQLiteDatabase db){
        db.execSQL(toDropSql(mTableCreator));
        return this;
    }

    public SQLTable alter(SQLiteDatabase db){
        String alterString = toAlterSql();
        if (!TextUtils.isEmpty(alterString))
            db.execSQL(alterString);
        return this;
    }

    protected String toAlterSql() {
        return null;
    }

    //保存(插入)
    public Uri save(TableSave tableSave) {
        Uri uri = parseUri(mTableCreator);
        return mContext.getContentResolver().insert(uri, tableSave.saveValues());
        //mDatabase.insert(mTableCreator.tableName(), null, tableSave.saveValues());
        //return this;
    }

    //批量更新
    public int update(TableSave tableSave, String selection, String[] selectionArgs) {
        Uri uri = parseUri(mTableCreator);
        return mContext.getContentResolver().update(uri, tableSave.saveValues(), selection, selectionArgs);
    }

    //更新单条记录
    public int update(Uri uri, TableSave tableSave) {
        return mContext.getContentResolver().update(uri, tableSave.saveValues(), null, null);
    }

    //查询
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String orderBy) {
        return mContext.getContentResolver().query(parseUri(mTableCreator), columns, selection, selectionArgs, orderBy);
        //return mDatabase.query(mTableCreator.tableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    //删除
    public int delete(String selection, String[] selectionArgs) {
        return mContext.getContentResolver().delete(parseUri(mTableCreator), selection, selectionArgs);
    }

    //删除单条记录
    public int delete(Uri uri) {
        return mContext.getContentResolver().delete(uri, null, null);
    }

    //查询全数据
    public Cursor queryFull() {
        return query(null, null, null, null);
    }

    //表创建语句
    private String toCreateSql(TableCreator tableCreator) {

        return "CREATE TABLE IF NOT EXISTS " + tableCreator.tableName() +
                "( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + tableCreator.tableColumns() + ")";

    }

    //表删除语句
    private String toDropSql(TableCreator tableCreator) {
        return "DROP TABLE IF EXISTS " + tableCreator.tableName();
    }

    /**
     * 该表加字段
     * @param column 列名
     * @param dataType 列数据类型
     * @return
     */
    public String addColumn(String column, String dataType) {
        return "ALTER TABLE " + mTableCreator.tableName() + " ADD COLUMN " + column + " " + dataType;
    }

    //表名解析到uri
    protected Uri parseUri(TableCreator tableCreator){
        return Uri.parse("content://" + DBProvider.AUTHORITY + "/" + tableCreator.tableName());
    }
}
