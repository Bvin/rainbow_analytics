package cn.rainbow.sdk.analytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class Config {

    /**启动时批量发送*/
    public static final int PUSH_STRATEGY_BATCH_BOOTSTRAP = 0;
    /**实时发送*/
    public static final int PUSH_STRATEGY_REAL_TIME = 1;
    /**实时发送仅wifi*/
    public static final int PUSH_STRATEGY_REAL_TIME_WIFI_ONLY = 2;

    private boolean mEnable = true;//默认开启
    private boolean mEnableDebugLog;
    private boolean mEnableCrashTrack;
    private boolean mIsTestEnv;
    private int mChannelId = 1;//渠道
    private boolean mSaveLocal = true;//默认开启
    private boolean mPushRemote = true;//默认开启
    private boolean mPushOnlyWifi;
    private boolean mUseJobScheduler;
    private long mDelayMsWhenPushLocal;
    private int mPushStrategy = PUSH_STRATEGY_BATCH_BOOTSTRAP;//默认启动时发送

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

    /**
     * 统计总开关（默认开启）.
     * @param enable true开启,false关闭
     */
    public void enable(boolean enable){
        mEnable = enable;
    }

    /**
     * 是否开启统计.
     * @return
     */
    public boolean isEnable(){
        return mEnable;
    }

    /**
     * 设置是否保存到本地数据库（默认开启）.
     * @param saveLocal true开启,false关闭
     */
    public void setSaveLocal(boolean saveLocal) {
        mSaveLocal = saveLocal;
    }

    /**
     * 是否推送到远程服务器（默认开启）..
     * @param pushRemote true开启,false关闭
     */
    public void setPushRemote(boolean pushRemote) {
        mPushRemote = pushRemote;
    }

    /**
     * 是否保存到本地.
     * @return
     */
    public boolean isSaveLocalEnable() {
        return mSaveLocal;
    }

    /**
     * 是否发送到服务器.
     * @return
     */
    public boolean isPushRemoteEnable() {
        return mPushRemote;
    }

    public int getPushStrategy() {
        return mPushStrategy;
    }

    /**
     * 设置上报策略.
     * @param pushStrategy 启动批量上报/实时上报
     */
    public void setPushStrategy(int pushStrategy) {
        mPushStrategy = pushStrategy;
    }

    /**
     * 设置是否只在wifi下上报.
     * @param pushOnlyWifi
     */
    public void setPushOnlyWifi(boolean pushOnlyWifi) {
        mPushOnlyWifi = pushOnlyWifi;
    }

    public boolean isPushOnlyWifi() {
        return mPushOnlyWifi;
    }

    public boolean isUseJobScheduler() {
        return mUseJobScheduler;
    }

    public void setUseJobScheduler(boolean useJobScheduler) {
        mUseJobScheduler = useJobScheduler;
    }

    public long getDelayMsWhenPushLocal() {
        return mDelayMsWhenPushLocal;
    }

    public void setDelayMsWhenPushLocal(long delayMsWhenPushLocal) {
        mDelayMsWhenPushLocal = delayMsWhenPushLocal;
    }

    @Override
    public String toString() {
        return "Config{" +
                "mEnable=" + mEnable +
                ", mEnableDebugLog=" + mEnableDebugLog +
                ", mEnableCrashTrack=" + mEnableCrashTrack +
                ", mIsTestEnv=" + mIsTestEnv +
                ", mChannelId=" + mChannelId +
                ", mSaveLocal=" + mSaveLocal +
                ", mPushRemote=" + mPushRemote +
                ", mPushOnlyWifi=" + mPushOnlyWifi +
                ", mPushStrategy=" + mPushStrategy +
                '}';
    }
}
