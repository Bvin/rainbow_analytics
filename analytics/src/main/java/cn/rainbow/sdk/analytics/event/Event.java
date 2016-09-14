package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.HttpParamModel;
import com.litesuits.http.request.param.NonHttpParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.TableSave;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;

/**
 * Created by 32967 on 2016/5/31.
 */
@HttpUri(ApiConfig.URL_REPORT)
public class Event implements TableSave ,HttpParamModel {

    @NonHttpParam
    private int mBaseIndex = -1;
    @NonHttpParam
    protected long mEventId;
    @NonHttpParam
    private String mEventName;
    @NonHttpParam
    private String mEventDesc;//data
    @NonHttpParam
    private int mEventType;
    @HttpParam(ApvReporter.Keys.ENTER_TIME)
    protected String mStartDate;
    @HttpParam(ApvReporter.Keys.LEAVE_TIME)
    protected String mEndDate;
    @NonHttpParam
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

            int columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_ID);
            if (isColumnExist(columnIndex)) mEventId = cursor.getLong(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_NAME);
            if (isColumnExist(columnIndex)) mEventName = cursor.getString(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_DESC);
            if (isColumnExist(columnIndex)) mEventDesc = cursor.getString(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_TYPE);
            if (isColumnExist(columnIndex)) mEventType = cursor.getInt(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_START_DATE);
            if (isColumnExist(columnIndex)) mStartDate = cursor.getString(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_END_DATE);
            if (isColumnExist(columnIndex)) mEndDate = cursor.getString(columnIndex);

            columnIndex = getColumnIndex(cursor, EventTable.Columns.EVENT_DURATION);
            if (isColumnExist(columnIndex)) mDuration = cursor.getLong(columnIndex);

        }
    }

    protected boolean isColumnExist(int columnIndex) {
        return columnIndex > -1;
    }

    protected int getColumnIndex(Cursor cursor, String columnName) {
        return cursor.getColumnIndex(columnName);
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

    public void putValidLong(ContentValues cv, String key, long value) {
        if (value > 0) cv.put(key, value);
    }

    public void putValidString(ContentValues cv, String key, String value) {
        if (!TextUtils.isEmpty(value)) cv.put(key, value);
    }

    /**
     * URL编码.
     * @param content 内容
     * @return 成功返回URL编码后的内容，否则返元原来内容
     */
    protected String urlEncode(String content){
        if (TextUtils.isEmpty(content)) return content;
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
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
