package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/18.
 */
public class THEvent extends Event{

    public static final int EVENT_ID_CLICK = 10001;
    public static final int EVENT_ID_SHOW = 10002;

    private int mEventType;

    private int mChannelId;

    private String mMerchantId;

    private String mUrl;

    private String mLink;

    private String mTraceNumber;

    private String mElementTraceNumber;

    private String mId;

    private String mUid;

    public THEvent(int eventId) {
        super("events");
        mEventType = eventId;
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

    public void setLink(String link) {
        mLink = link;
    }

    public void setTraceNumber(String traceNumber) {
        mTraceNumber = traceNumber;
    }

    public void setElementTraceNumber(String elementTraceNumber) {
        mElementTraceNumber = elementTraceNumber;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(getName());
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "mid", mMerchantId);
        putValue(sb, "u", mUrl, true);
        putValue(sb, "l", mLink, true);
        putValue(sb, "e", String.valueOf(mEventType));
        putValue(sb, "tn", mTraceNumber);
        putValue(sb, "etn", mElementTraceNumber);
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        return sb.toString();
    }
}
