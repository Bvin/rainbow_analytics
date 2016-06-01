package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;

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
    public String tableName() {
        return "app";
    }

    @Override
    public String defineFields() {
        return Columns.APP_ID + " TEXT," +
                Columns.APP_NAME + " TEXT," +
                Columns.APP_VERSION + " INT," +
                Columns.APP_VERSION_NAME + " TEXT," +
                Columns.SDK_MIN + " INT," +
                Columns.SDK_TARGET + " INT," +
                Columns.BUILD_TYPE + " TEXT," +
                Columns.BUILD_VARIANT + " TEXT," +
                Columns.SYSTEM_VERSION + " INT," +
                Columns.DEVICE_MODEL + " TEXT," +
                Columns.SCREEN_SIZE + " TEXT," +
                Event.Columns.EVENT_START_DATE + " TEXT," +
                Event.Columns.EVENT_END_DATE + " TEXT," +
                Event.Columns.EVENT_DURATION + " LONG";
    }

    @Override
    public ContentValues values() {
        ContentValues cv = new ContentValues();
        putValidInt(cv, Columns.APP_ID, mAppId);
        putValidString(cv, Columns.APP_NAME, mAppName);
        putValidInt(cv, Columns.APP_VERSION, mAppVersion);
        putValidString(cv, Columns.APP_VERSION_NAME, mAppVersionName);
        putValidInt(cv, Columns.SDK_MIN, mSdkMin);
        putValidInt(cv, Columns.SDK_TARGET, mSdkTarget);
        putValidString(cv, Columns.BUILD_TYPE, mBuildType);
        putValidString(cv, Columns.BUILD_VARIANT, mBuildVariant);
        putValidInt(cv, Columns.SYSTEM_VERSION, mSystemVersion);
        putValidString(cv, Columns.DEVICE_MODEL, mDeviceModel);
        if (mScreenSize != null) {
            putValidString(cv, Columns.SCREEN_SIZE, mScreenSize[0] + "," + mScreenSize[1]);
        }
        putValidString(cv, Event.Columns.EVENT_START_DATE, mStartDate);
        putValidString(cv, Event.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(Event.Columns.EVENT_DURATION, mDuration);
        return cv;
    }

    public class Columns {
        public static final String APP_ID = "app_id";
        public static final String APP_NAME = "app_name";
        public static final String APP_VERSION = "app_version";
        public static final String APP_VERSION_NAME = "app_version_name";
        public static final String SDK_MIN = "sdk_min";
        public static final String SDK_TARGET = "sdk_target";
        public static final String BUILD_TYPE = "build_type";
        public static final String BUILD_VARIANT = "build_variant";
        public static final String SYSTEM_VERSION = "system_version";
        public static final String DEVICE_MODEL = "device_model";
        public static final String SCREEN_SIZE = "screen_size";
    }
}
