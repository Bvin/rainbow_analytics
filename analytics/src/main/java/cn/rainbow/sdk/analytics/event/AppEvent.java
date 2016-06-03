package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;

import cn.rainbow.sdk.analytics.data.local.db.AppTable;
import cn.rainbow.sdk.analytics.data.local.db.EventTable;

/**
 * Created by bvin on 2016/6/1.
 */
public class AppEvent extends Event {
    public static final long EVENT_ID = 1;

    private int mAppId;
    private String mAppName;
    private int mAppVersion;
    private String mAppVersionName;
    private int mSdkMin;
    private int mSdkTarget;
    private String mBuildType;
    private String mBuildVariant;
    private int mSystemVersion;
    private String mDeviceModel;
    private int[] mScreenSize;

    public AppEvent() {
        //empty construct,just use to create sql table when DBHelper create.
    }

    public AppEvent(int appId) {
        mAppId = appId;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public void setAppVersion(int appVersion) {
        mAppVersion = appVersion;
    }

    public void setAppVersionName(String appVersionName) {
        mAppVersionName = appVersionName;
    }

    public void setSdkMin(int sdkMin) {
        mSdkMin = sdkMin;
    }

    public void setSdkTarget(int sdkTarget) {
        mSdkTarget = sdkTarget;
    }

    public void setBuildType(String buildType) {
        mBuildType = buildType;
    }

    public void setBuildVariant(String buildVariant) {
        mBuildVariant = buildVariant;
    }

    public void setSystemVersion(int systemVersion) {
        mSystemVersion = systemVersion;
    }

    public void setDeviceModel(String deviceModel) {
        mDeviceModel = deviceModel;
    }

    public void setScreenSize(int[] screenSize) {
        mScreenSize = screenSize;
    }


    @Override
    public ContentValues saveValues() {
        ContentValues cv = new ContentValues();
        putValidInt(cv, AppTable.Columns.APP_ID, mAppId);
        putValidString(cv, AppTable.Columns.APP_NAME, mAppName);
        putValidInt(cv, AppTable.Columns.APP_VERSION, mAppVersion);
        putValidString(cv, AppTable.Columns.APP_VERSION_NAME, mAppVersionName);
        putValidInt(cv, AppTable.Columns.SDK_MIN, mSdkMin);
        putValidInt(cv, AppTable.Columns.SDK_TARGET, mSdkTarget);
        putValidString(cv, AppTable.Columns.BUILD_TYPE, mBuildType);
        putValidString(cv, AppTable.Columns.BUILD_VARIANT, mBuildVariant);
        putValidInt(cv, AppTable.Columns.SYSTEM_VERSION, mSystemVersion);
        putValidString(cv, AppTable.Columns.DEVICE_MODEL, mDeviceModel);
        if (mScreenSize != null) {
            putValidString(cv, AppTable.Columns.SCREEN_SIZE, mScreenSize[0] + "," + mScreenSize[1]);
        }
        putValidString(cv, EventTable.Columns.EVENT_START_DATE, mStartDate);
        putValidString(cv, EventTable.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(EventTable.Columns.EVENT_DURATION, mDuration);
        return cv;
    }

}
