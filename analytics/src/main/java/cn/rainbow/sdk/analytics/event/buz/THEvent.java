package cn.rainbow.sdk.analytics.event.buz;

import android.database.Cursor;

import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

/**
 * Created by bvin on 2016/8/10.
 */
public class THEvent extends Event{

    public static final int EVENT_ID_CLICK = 10001;
    public static final int EVENT_ID_SHOW = 10002;

    protected int mChannelId;
    protected String mMerchantId;
    private String mUrl;

    private String mLink;
    private String mTraceNumber;

    private String mId;
    private String mUid;

    public THEvent(long eventId) {
        super(eventId);
    }

    public THEvent(Cursor cursor) {
        super(cursor);
        if (cursor != null) {
            initBaseColumns(cursor);
            mChannelId = cursor.getInt(cursor.getColumnIndex(THEventReport.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.MERCHANT_ID));
            mUrl =  cursor.getString(cursor.getColumnIndex(THEventReport.Keys.PAGE));
            mLink =  cursor.getString(cursor.getColumnIndex(THEventReport.Keys.LINK));

            mId = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.DEVICE_ID));
            mUid = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.USER_ID));
            mTraceNumber = cursor.getString(cursor.getColumnIndex(THEventReport.Keys.TRACE_NUMBER));
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

}
