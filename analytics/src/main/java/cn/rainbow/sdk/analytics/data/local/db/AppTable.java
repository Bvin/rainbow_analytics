package cn.rainbow.sdk.analytics.data.local.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.event.AppEvent;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/1.
 */
public class AppTable extends EventTable{

    public AppTable(TableCreator tableCreator, SQLiteDatabase database) {
        super(tableCreator, database);
    }

    public AppTable(SQLiteDatabase database) {
        this(new AppEvent(), database);
    }

    public void insert(){
        save();//course save() means insert.
    }

    public void update() {
        Cursor cursor = mDatabase.rawQuery("select * from " + mTableCreator.tableName() + " order by " + SQLTable._ID + " desc limit 0,1", null);
        if (cursor.moveToFirst()) {
            String endDate = cursor.getString(cursor.getColumnIndex(Event.Columns.EVENT_END_DATE));
            long duration = cursor.getLong(cursor.getColumnIndex(Event.Columns.EVENT_DURATION));
            if (TextUtils.isEmpty(endDate) || duration == 0) {
                int _id = cursor.getInt(cursor.getColumnIndex(SQLTable._ID));
                mDatabase.update(mTableCreator.tableName(), mTableCreator.values(),
                        SQLTable._ID + "=?", new String[]{String.valueOf(_id)});
            }else {
                //如果有应该新插入一条..
            }
        }
        cursor.close();
    }
}
