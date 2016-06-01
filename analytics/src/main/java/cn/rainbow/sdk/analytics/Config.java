package cn.rainbow.sdk.analytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class Config {

    private boolean mEnableDebugLog;

    public void enableDebugLog(boolean enable){
        mEnableDebugLog = enable;
    }

    public boolean isEnableDebugLog(){
        return mEnableDebugLog;
    }
}
