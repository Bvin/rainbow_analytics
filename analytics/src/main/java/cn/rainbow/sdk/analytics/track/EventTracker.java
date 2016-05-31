package cn.rainbow.sdk.analytics.track;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.rainbow.sdk.analytics.data.local.db.DBHelper;
import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by 32967 on 2016/5/31.
 */
public abstract class EventTracker<T extends Event> {

    private SimpleDateFormat mDateFormat;
    private long mStartMillis;
    private T mEvent;

    protected Context mContext;
    protected long mEventId;
    protected String mEventName;

    public EventTracker(long eventId, String eventName) {
        mEventId = eventId;
        mEventName = eventName;
    }

    public void attachContext(Context context){
        mContext = context;
    }

    public void onEventStart(){
        if (mEvent == null) {
            mEvent = createEvent();
        }
        mEvent.setStartDate(getCurrentDate());
        mStartMillis = System.currentTimeMillis();
    }

    public abstract T createEvent();

    public void onEvent(){

    }

    public void onEventEnd(){
        //事件统计结束，保存到数据库
        mEvent.setEndDate(getCurrentDate());
        long duration = System.currentTimeMillis() - mStartMillis;
        mEvent.setDuration(duration);

        save();
    }

    protected void save() {
        DBHelper dbHelper = new DBHelper(mContext);
        EventTable eventTable = new EventTable(mEvent, dbHelper.getWritableDatabase());
        eventTable.save();
    }

    private String getCurrentDate(){
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return mDateFormat.format(new Date());
    }
}
