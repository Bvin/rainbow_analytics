package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.rainbow.sdk.analytics.data.local.db.DBHelper;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/5/31.
 */
public abstract class AbsEventTracker<T extends Event> {

    private SimpleDateFormat mDateFormat;
    private long mStartMillis;
    private T mEvent;

    protected Context mContext;
    protected long mEventId;
    protected String mEventName;

    public AbsEventTracker(long eventId, String eventName) {
        mEventId = eventId;
        mEventName = eventName;
    }

    public void attachContext(Context context){
        mContext = context;
    }

    public abstract T createEvent();
    public abstract SQLTable createTable(T event, SQLiteDatabase database);

    /**
     * 事件开始.
     * <p>调用此方法之前一定要确保createEvent()中的事件已被创建
     */
    public void onEventStart(){
        if (mEvent == null) {
            mEvent = createEvent();
        }
        if (mEvent == null)
            throw new RuntimeException("event must be build before call onEventStart() method!");
        mEvent.setStartDate(getCurrentDate());
        mStartMillis = System.currentTimeMillis();
    }

    public void onEvent(){

    }

    public void onEventEnd(){
        if (mEvent == null)
            throw new RuntimeException("event object may be not created or be recycled!");

        //事件统计结束，保存到数据库
        mEvent.setEndDate(getCurrentDate());
        long duration = System.currentTimeMillis() - mStartMillis;
        mEvent.setDuration(duration);

        save();
    }

    protected void save() {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLTable table = createTable(mEvent, dbHelper.getWritableDatabase());
        table.save(mEvent);
        //table.close();
    }

    protected void push(){

    }

    private String getCurrentDate(){
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return mDateFormat.format(new Date());
    }
}
