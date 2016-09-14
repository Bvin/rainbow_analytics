package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.database.Cursor;

import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.NonHttpParam;

import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

/**
 * Created by bvin on 2016/8/10.
 */
public class THEvent extends Event{

    public static final int EVENT_ID_CLICK = 10001;
    public static final int EVENT_ID_SHOW = 10002;

    @HttpParam("type")
    private String mType = "events";

    @HttpParam(THEventReport.Keys.CHANNEL_ID)
    protected int mChannelId;

    @HttpParam(THEventReport.Keys.MERCHANT_ID)
    protected String mMerchantId;

    @HttpParam(THEventReport.Keys.PAGE)
    private String mUrl;

    @HttpParam(THEventReport.Keys.LINK)
    private String mLink;

    @HttpParam(THEventReport.Keys.TRACE_NUMBER)
    private String mTraceNumber;

    @HttpParam(THEventReport.Keys.ELEMENT_TRACE_NUMBER)
    private String mElementTraceNumber;

    @HttpParam(THEventReport.Keys.DEVICE_ID)
    private String mId;

    @HttpParam(THEventReport.Keys.USER_ID)
    private String mUid;

    @NonHttpParam
    private ContentValues mValues;

    public THEvent(long eventId) {
        super(eventId);
    }

    public THEvent(Cursor cursor) {
        if (cursor != null) {
            initBaseColumns(cursor);

            int columnIndex = getColumnIndex(cursor, THEventReport.Keys.EVENT_ID);
            if (isColumnExist(columnIndex)) mEventId = cursor.getLong(columnIndex);

            mChannelId = cursor.getInt(cursor.getColumnIndex(THEventReport.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.MERCHANT_ID));
            mUrl =  cursor.getString(cursor.getColumnIndex(THEventReport.Keys.PAGE));
            mLink =  cursor.getString(cursor.getColumnIndex(THEventReport.Keys.LINK));

            mId = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.DEVICE_ID));
            mUid = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.USER_ID));
            mTraceNumber = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.TRACE_NUMBER));
            mElementTraceNumber = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.ELEMENT_TRACE_NUMBER));
        }
    }

    public int getChannelId() {
        return mChannelId;
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTraceNumber() {
        return mTraceNumber;
    }

    public void setTraceNumber(String traceNumber) {
        mTraceNumber = traceNumber;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getElementTraceNumber() {
        return mElementTraceNumber;
    }

    public void setElementTraceNumber(String elementTraceNumber) {
        mElementTraceNumber = elementTraceNumber;
    }

    @Override
    public ContentValues saveValues() {
        if (mValues == null) {
            mValues = new ContentValues();
            putValidInt(mValues, THEventReport.Keys.CHANNEL_ID, mChannelId);
            putValidString(mValues, THEventReport.Keys.MERCHANT_ID, mMerchantId);
            putValidLong(mValues, THEventReport.Keys.EVENT_ID, getEventId());
            putValidString(mValues, THEventReport.Keys.PAGE, mUrl);
            putValidString(mValues, THEventReport.Keys.LINK, mLink);
            putValidString(mValues, THEventReport.Keys.DEVICE_ID, mId);
            putValidString(mValues, THEventReport.Keys.USER_ID, mUid);
            putValidString(mValues, THEventReport.Keys.TRACE_NUMBER, mTraceNumber);
            putValidString(mValues, THEventReport.Keys.ELEMENT_TRACE_NUMBER, mElementTraceNumber);
        }
        return mValues;
    }
}
