package cn.rainbow.sdk.analytics.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.SparseArray;

import cn.rainbow.sdk.analytics.persistence.db.DBHelper;
import cn.rainbow.sdk.analytics.persistence.db.table.EventTable;

/**
 * 持久化服务.
 * <br>Created by bvin on 2016/9/13.
 * <ul>
 *     <li>如果是独立的操作一定要记得，执行完之后手动调用end()方法释放数据库。
 *     <li>如果是可预知多项连续的数据库操作，可在所有操作完了之后再调用end()方法，提交事务。
 *     <li>如果是长时并且时间不可预知的数据库操作，将会在根据执行操作次数是否达到预设次数，达到就将自动释放资源，以免一直占用资源。
 * </ul>
 */
public class PersistenceService {

    private static PersistenceService ourInstance;

    public static PersistenceService getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PersistenceService(context);
        }
        return ourInstance;
    }


    //SQL操作多少次让数据库释放
    public static final int SQL_TIMES_FREE = 10;
    public static final int MAX_QUERY_SIZE = 50;

    private Context mContext;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mDatabase;
    private int mCounter;
    private boolean mForceClose;

    private PersistenceService(Context context) {
        mContext = context;
    }

    /**
     * 释放数据库.
     */
    public void end() {
        //mForceClose = true;
        release();
    }

    //保存
    public void save(final Persistable persistable) {
        if (persistable != null && !TextUtils.isEmpty(persistable.toPersistableString())) {
            exeSql(new SQLExecutor() {
                @Override
                public boolean execute(SQLiteDatabase db) {
                    db.execSQL("insert into " + EventTable.TABLE_NAME + "(" + EventTable.COLUMN_EVENT + ") values ('" + persistable.toPersistableString() + "')");
                    return false;
                }
            });
        }
    }

    //SQL执行管道
    private void exeSql(SQLExecutor executor) {
        if (mCounter < SQL_TIMES_FREE) {//为了不让数据库长期驻扎内存，设定执行次数，达到设定次数释放数据库
            if (mDatabase == null) {
                mSQLiteOpenHelper = new DBHelper(mContext);
                mDatabase = mSQLiteOpenHelper.getWritableDatabase();
                mDatabase.beginTransaction();
            }
            if (executor.execute(mDatabase)) {
                mCounter++;
            }
            if (mForceClose) {//强制释放
                release();
                mForceClose = false;
            }
        } else {
            release();
        }
    }

    //查询
    public void query(final SQLCallback<SparseArray<String>> callback) {
        exeSql(new SQLExecutor() {
            @Override
            public boolean execute(SQLiteDatabase db) {
                Cursor cursor = db.rawQuery("select * from " + EventTable.TABLE_NAME + " desc limit 0," + MAX_QUERY_SIZE + "", null);
                if (cursor != null) {
                    SparseArray<String> result = new SparseArray<>();
                    if (cursor.moveToFirst()) {
                        do {
                            result.put(cursor.getInt(EventTable.COLUMN_INDEX_ID), cursor.getString(EventTable.COLUMN_INDEX_EVENT));
                        } while (cursor.moveToNext());
                    }
                    callback.callback(result);
                    cursor.close();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    //删除
    public void delete(final int rowId){
        exeSql(new SQLExecutor() {
            @Override
            public boolean execute(SQLiteDatabase db) {
                db.execSQL("delete from " + EventTable.TABLE_NAME + " where " + EventTable.COLUMN_ID + "=" + rowId);
                return true;
            }
        });
    }

    private void release() {
        mCounter = 0;
        if (mDatabase != null) {
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
            mDatabase.close();
            mDatabase = null;
        }
        if (mSQLiteOpenHelper != null) {
            mSQLiteOpenHelper.close();
            mSQLiteOpenHelper = null;
        }
    }

    interface SQLExecutor{
        boolean execute(SQLiteDatabase db);
    }

    public interface SQLCallback<T>{
        void callback(T t);
    }
}
