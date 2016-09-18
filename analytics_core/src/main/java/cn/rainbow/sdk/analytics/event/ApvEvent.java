package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/14.
 */
public class ApvEvent extends Event {

    private int mChannelId;
    private String mMerchantId;
    private String mUrl;
    private String mAppVersion;
    private String mDevice;
    private String mDeviceId;
    private String mSystem;
    private String mSystemVersion;
    private String mEnterTime;
    private String mLeaveTime;
    private String mTraceNumber;

    public ApvEvent() {
        super("apv");
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

    public void setEnterTime(String enterTime) {
        mEnterTime = enterTime;
    }

    public void setLeaveTime(String leaveTime) {
        mLeaveTime = leaveTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(getName());
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "mid", mMerchantId);
        putValue(sb, "u", mUrl, true);
        putValue(sb, "v", mAppVersion);
        putValue(sb, "mb", mDevice);
        putValue(sb, "id", mDeviceId);
        putValue(sb, "o", mSystem);
        putValue(sb, "ov", mSystemVersion);
        putValue(sb, "et", mEnterTime, true);
        putValue(sb, "lt", mLeaveTime, true);
        putValue(sb, "tn", mTraceNumber);
        return sb.toString();
    }


}
