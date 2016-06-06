package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;

import cn.rainbow.sdk.analytics.data.local.db.AppTable;
import cn.rainbow.sdk.analytics.data.local.db.CrashTable;
import cn.rainbow.sdk.analytics.data.local.db.EventTable;

/**
 * Created by bvin on 2016/6/2.
 */
public class CrashEvent extends Event{

    public static final long EVENT_ID = 2;
    public static final String EVENT_NAME = "崩溃统计";

    private  String mCrashLog;

    private int mAppVersion;
    private String mAppVersionName;

    //user_info
    private String mUserId;

    //build_info
    private String mBuildType;
    private String mBuildVariant;

    //system_info
    private int mSystemVersion;

    //device_env
    private String mNetEnv;
    private String mDeviceModel;
    private int[] mScreenSize;

    public CrashEvent(String crashLog) {
        mCrashLog = crashLog;
    }


    public void setAppVersionName(String appVersionName) {
        mAppVersionName = appVersionName;
    }

    public void setAppVersion(int appVersion) {
        mAppVersion = appVersion;
    }

    public void setScreenSize(int[] screenSize) {
        mScreenSize = screenSize;
    }

    public void setDeviceModel(String deviceModel) {
        mDeviceModel = deviceModel;
    }

    public void setUserId(String userId) {
        mUserId = userId;
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

    public void setNetEnv(String netEnv) {
        mNetEnv = netEnv;
    }

    @Override
    public ContentValues saveValues() {
        ContentValues cv = new ContentValues();
        putValidString(cv, CrashTable.Columns.CRASH_INFO, mCrashLog);
        putValidInt(cv, AppTable.Columns.APP_VERSION, mAppVersion);
        putValidString(cv, AppTable.Columns.APP_VERSION_NAME, mAppVersionName);
        putValidString(cv, AppTable.Columns.BUILD_TYPE, mBuildType);
        putValidString(cv, AppTable.Columns.BUILD_VARIANT, mBuildVariant);
        putValidInt(cv, AppTable.Columns.SYSTEM_VERSION, mSystemVersion);
        putValidString(cv, AppTable.Columns.DEVICE_MODEL, mDeviceModel);
        if (mScreenSize != null) {
            putValidString(cv, AppTable.Columns.SCREEN_SIZE, mScreenSize[0] + "," + mScreenSize[1]);
        }
        putValidString(cv, CrashTable.Columns.USER_ID, mUserId);
        putValidString(cv, CrashTable.Columns.NET_ENV, mNetEnv);
        putValidString(cv, EventTable.Columns.EVENT_START_DATE, mStartDate);
        putValidString(cv, EventTable.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(EventTable.Columns.EVENT_DURATION, mDuration);
        return cv;
    }
}
