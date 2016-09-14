package cn.rainbow.sdk.analytics.data.local.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/17.
 */
public abstract class AbsEventTable<T extends Event> extends SQLTable implements TableCreator{


    public AbsEventTable(Context context) {
        super(context);
        setTableCreator(this);
    }

    /**
     * 查询.
     * @return 数据集
     */
    public List<T> query() {
        Cursor cursor = queryFull();
        if (cursor != null && cursor.moveToFirst()) {
            List<T> eventList = new ArrayList<>();
            int i = 0;
            do {
                if (i >= 10) break;
                T event = newEvent(cursor);
                if (event != null) {
                    eventList.add(event);
                    i++;
                }
            } while (cursor.moveToNext());
            cursor.close();//采集完数据，关闭cursor
            return eventList;
        }
        return null;
    }

    /**
     * 根据cursor还原Event.
     * @param cursor 数据
     * @return Event
     */
    protected abstract T newEvent(Cursor cursor);

    /**
     * 删除.
     * @param t 事件
     */
    public void delete(T t) {
        if (t != null && t.getBaseColumns() > 0) {
            int rowNumber;
            //条件删除
            //rowNumber = delete(SQLTable._ID + "=?", new String[]{t.getBaseColumns() + ""});
            //删除记录
            Uri tableUri = parseUri(this);
            rowNumber = delete(ContentUris.withAppendedId(tableUri, t.getBaseColumns()));

            Log.d("delete",rowNumber+"");

        }
    }
}
