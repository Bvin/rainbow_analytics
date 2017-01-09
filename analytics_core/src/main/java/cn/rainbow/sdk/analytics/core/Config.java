package cn.rainbow.sdk.analytics.core;

/**
 * Created by bvin on 2016/9/14.
 */
public class Config {

    private boolean mEnable;
    private boolean mRealTime;
    private boolean mTestEnv;
    private boolean mPushOnWifi;
    private long mTaskInterval = 1000;
    private String mHost;

    public void setRealTime(boolean realTime) {
        mRealTime = realTime;
    }

    public boolean isRealTime() {
        return mRealTime;
    }

    public boolean isTestEnv() {
        return mTestEnv;
    }

    public void setTestEnv(boolean testEnv) {
        mTestEnv = testEnv;
    }

    public boolean isEnable() {
        return mEnable;
    }

    public void setEnable(boolean enable) {
        mEnable = enable;
    }

    public boolean isPushOnWifi() {
        return mPushOnWifi;
    }

    public void setPushOnWifi(boolean pushOnWifi) {
        mPushOnWifi = pushOnWifi;
    }

    public long getTaskInterval() {
        return mTaskInterval;
    }

    public void setTaskInterval(long taskInterval) {
        mTaskInterval = taskInterval;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        mHost = host;
    }
}
