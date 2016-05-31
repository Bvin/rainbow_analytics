package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.db.TableCreator;

/**
 * Created by 32967 on 2016/5/31.
 */
public class Event implements TableCreator{

    private long mEventId;
    private String mEventName;
    private String mEventDesc;//data
    private int mEventType;
    private String mStartDate;
    private String mEndDate;
    private long mDuration;

    public Event() {
        //empty construct
    }

    public Event(long eventId) {
        mEventId = eventId;
    }

    public Event(long eventId, String eventName, String eventDesc) {
        mEventId = eventId;
        mEventName = eventName;
        mEventDesc = eventDesc;
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
    public String tableName() {
        return "event";
    }

    @Override
    public String defineFields() {
        return Columns.EVENT_ID + " LONG," +
                Columns.EVENT_NAME + " TEXT," +
                Columns.EVENT_DESC + " TEXT," +
                Columns.EVENT_TYPE + " INT," +
                Columns.EVENT_START_DATE + " TEXT," +
                Columns.EVENT_END_DATE + " TEXT," +
                Columns.EVENT_DURATION + " LONG";
    }

    @Override
    public ContentValues values() {
        ContentValues cv = new ContentValues();
        cv.put(Columns.EVENT_ID, mEventId);
        if (!TextUtils.isEmpty(mEventName)) cv.put(Columns.EVENT_NAME, mEventName);
        if (!TextUtils.isEmpty(mEventDesc)) cv.put(Columns.EVENT_DESC, mEventDesc);
        cv.put(Columns.EVENT_TYPE, mEventType);
        if (!TextUtils.isEmpty(mStartDate)) cv.put(Columns.EVENT_START_DATE, mStartDate);
        if (!TextUtils.isEmpty(mEndDate)) cv.put(Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(Columns.EVENT_DURATION, mDuration);
        return cv;
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

    class Columns{
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_NAME = "event_name";
        public static final String EVENT_DESC = "event_desc";
        public static final String EVENT_TYPE = "event_type";
        public static final String EVENT_START_DATE = "start_date";
        public static final String EVENT_END_DATE = "end_date";
        public static final String EVENT_DURATION = "duration";
    }
}
