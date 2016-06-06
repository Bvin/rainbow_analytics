package cn.rainbow.sdk.analytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class Config {

    private boolean mEnableDebugLog;
    private boolean mEnableCrashTrack;

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
}
