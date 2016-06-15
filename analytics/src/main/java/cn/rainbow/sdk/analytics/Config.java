package cn.rainbow.sdk.analytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class Config {

    private boolean mEnableDebugLog;
    private boolean mEnableCrashTrack;
    private boolean mIsTestEnv;
    private int mChannelId;

    public void enableDebugLog(boolean enable){
        mEnableDebugLog = enable;
    }

    public boolean isEnableDebugLog(){
        return mEnableDebugLog;
    }

    public void enableCrashTrack(boolean enable){
        mEnableCrashTrack = enable;
    }

    public boolean isEnableCrashTrack(){
        return mEnableCrashTrack;
    }

    /**
     * 是否测试环境.
     * @return
     */
    public boolean isTestEnv() {
        return mIsTestEnv;
    }

    /**
     * 设置测试环境.
     * <p><i>测试环境数据将会上报到测试服务器.</i>
     * @param testEnv 是否在测试环境运行
     */
    public void setTestEnv(boolean testEnv) {
        mIsTestEnv = testEnv;
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public int getChannelId() {
        return mChannelId;
    }
}
