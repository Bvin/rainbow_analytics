package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;

/**
 * Created by bvin on 2016/6/2.
 */
public class CrashEvent extends Event{

    private  String mCrashLog;

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


    public CrashEvent() {
        //empty construct,just use to create sql table when DBHelper create.
    }

    public CrashEvent(String crashLog) {
        mCrashLog = crashLog;
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
    public String tableName() {
        return super.tableName();
    }

    @Override
    public String defineFields() {
        return super.defineFields();
    }

    @Override
    public ContentValues values() {
        return super.values();
    }
}
