package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.database.Cursor;

import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;

/**
 * Created by bvin on 2016/6/12.
 * <p>天虹页面统计事件
 */
public class THPageEvent extends PageEvent {

    protected int mChannelId;
    protected String mMerchantId;
    private String mUrl;
    private String mAppVersion;
    private String mDevice;
    private String mDeviceId;
    private String mSystem;
    private String mSystemVersion;
    private String mTraceNumber;
    private ContentValues mValues;

    public THPageEvent() {
    }

    public THPageEvent(Cursor cursor) {
        if (cursor != null) {
            initBaseColumns(cursor);
            mChannelId = cursor.getInt(cursor.getColumnIndex(ApvReporter.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.MERCHANT_ID));
            mUrl = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.PAGE));
            mAppVersion = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.APP_VERSION));
            mDevice = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.MOBILE));
            mDeviceId = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.DEVICE_ID));
            mSystem = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.OS));
            mSystemVersion = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.OS_VERSION));
            mStartDate = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.ENTER_TIME));
            mEndDate = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.LEAVE_TIME));
            mTraceNumber = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.TRACE_NUMBER));
        }
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setAppVersion(String appVersion) {
        mAppVersion = appVersion;
    }

    public void setDevice(String device) {
        mDevice = device;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public void setSystem(String system) {
        mSystem = system;
    }

    public void setSystemVersion(String systemVersion) {
        mSystemVersion = systemVersion;
    }

    public void setTraceNumber(String traceNumber) {
        mTraceNumber = traceNumber;
    }

    public String getTraceNumber() {
        return mTraceNumber;
    }

    @Override
    public ContentValues saveValues() {
        if (mValues == null) {
            mValues = new ContentValues();
            putValidInt(mValues, ApvReporter.Keys.CHANNEL_ID, mChannelId);
            putValidString(mValues, ApvReporter.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, ApvReporter.Keys.PAGE, mUrl);
            putValidString(mValues, ApvReporter.Keys.APP_VERSION, mAppVersion);
            putValidString(mValues, ApvReporter.Keys.ENTER_TIME, mStartDate);
            putValidString(mValues, ApvReporter.Keys.LEAVE_TIME, mEndDate);
            putValidString(mValues, ApvReporter.Keys.MOBILE, mDevice);
            putValidString(mValues, ApvReporter.Keys.OS, mSystem);
            putValidString(mValues, ApvReporter.Keys.OS_VERSION, mSystemVersion);
            putValidString(mValues, ApvReporter.Keys.DEVICE_ID, mDeviceId);
            putValidString(mValues, ApvReporter.Keys.TRACE_NUMBER, mTraceNumber);
        }
        return mValues;
    }

    public int getChannelId() {
        return mChannelId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public String getDevice() {
        return mDevice;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getSystem() {
        return mSystem;
    }

    public String getSystemVersion() {
        return mSystemVersion;
    }
}
