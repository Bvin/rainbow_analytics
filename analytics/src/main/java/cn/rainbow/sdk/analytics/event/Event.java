package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.TableSave;

/**
 * Created by 32967 on 2016/5/31.
 */
public class Event implements TableSave{

    private int mBaseIndex = -1;
    private long mEventId;
    private String mEventName;
    private String mEventDesc;//data
    private int mEventType;
    protected String mStartDate;
    protected String mEndDate;
    protected long mDuration;

    public Event() {
        //empty construct,just use to create sql table when DBHelper create.
    }

    public Event(long eventId) {
        mEventId = eventId;
    }

    public Event(long eventId, String eventName, String eventDesc) {
        mEventId = eventId;
        mEventName = eventName;
        mEventDesc = eventDesc;
    }

    /**
     * 通过数据库结果集还原事件.
     * @param cursor 不要对cursor做任何写操作，只能做读操作
     */
    public Event(Cursor cursor){
        if (cursor != null) {
            initBaseColumns(cursor);
            mEventId = cursor.getLong(cursor.getColumnIndex(EventTable.Columns.EVENT_ID));
            mEventName = cursor.getString(cursor.getColumnIndex(EventTable.Columns.EVENT_NAME));
            mEventDesc = cursor.getString(cursor.getColumnIndex(EventTable.Columns.EVENT_DESC));
            mEventType = cursor.getInt(cursor.getColumnIndex(EventTable.Columns.EVENT_TYPE));
            mStartDate = cursor.getString(cursor.getColumnIndex(EventTable.Columns.EVENT_START_DATE));
            mEndDate = cursor.getString(cursor.getColumnIndex(EventTable.Columns.EVENT_END_DATE));
            mDuration = cursor.getLong(cursor.getColumnIndex(EventTable.Columns.EVENT_DURATION));
        }
    }

    protected void initBaseColumns(Cursor cursor){
        mBaseIndex = cursor.getInt(cursor.getColumnIndex(SQLTable._ID));
    }

    public int getBaseColumns(){
        return mBaseIndex;
    }

    public void setEventType(int eventType) {
        mEventType = eventType;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getEventId() {
        return mEventId;
    }

    public String getEventName() {
        return mEventName;
    }

    public String getEventDesc() {
        return mEventDesc;
    }

    public int getEventType() {
        return mEventType;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    @Override
    public ContentValues saveValues() {
        ContentValues cv = new ContentValues();
        cv.put(EventTable.Columns.EVENT_ID, mEventId);
        if (!TextUtils.isEmpty(mEventName)) cv.put(EventTable.Columns.EVENT_NAME, mEventName);
        if (!TextUtils.isEmpty(mEventDesc)) cv.put(EventTable.Columns.EVENT_DESC, mEventDesc);
        cv.put(EventTable.Columns.EVENT_TYPE, mEventType);
        if (!TextUtils.isEmpty(mStartDate)) cv.put(EventTable.Columns.EVENT_START_DATE, mStartDate);
        if (!TextUtils.isEmpty(mEndDate)) cv.put(EventTable.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(EventTable.Columns.EVENT_DURATION, mDuration);
        return cv;
    }

    public void putValidInt(ContentValues cv, String key, int value) {
        if (value > 0) cv.put(key, value);
    }

    public void putValidString(ContentValues cv, String key, String value) {
        if (!TextUtils.isEmpty(value)) cv.put(key, value);
    }

    @Override
    public String toString() {
        return "Event{" +
                "mEventId=" + mEventId +
                ", mEventName='" + mEventName + '\'' +
                ", mEventDesc='" + mEventDesc + '\'' +
                ", mEventType=" + mEventType +
                ", mStartDate='" + mStartDate + '\'' +
                ", mEndDate='" + mEndDate + '\'' +
                ", mDuration=" + mDuration +
                '}';
    }


}
