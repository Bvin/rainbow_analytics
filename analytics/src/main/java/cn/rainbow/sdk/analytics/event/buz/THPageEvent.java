package cn.rainbow.sdk.analytics.event.buz;

import cn.rainbow.sdk.analytics.event.PageEvent;

/**
 * Created by bvin on 2016/6/12.
 * <p>天虹页面统计事件
 */
public class THPageEvent extends PageEvent {

    private String mChannelId;
    private String mMerchantId;
    private String mUrl;
    private String mAppVersion;
    private String mDevice;
    private String mDeviceId;
    private String mSystem;
    private String mSystemVersion;

    public void setChannelId(String channelId) {
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


    public String getChannelId() {
        return mChannelId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public String getDevice() {
        return mDevice;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getSystem() {
        return mSystem;
    }

    public String getSystemVersion() {
        return mSystemVersion;
    }
}
