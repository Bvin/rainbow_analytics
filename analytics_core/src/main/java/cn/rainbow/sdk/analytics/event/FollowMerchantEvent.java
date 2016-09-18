package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/18.
 */
public class FollowMerchantEvent extends Event{

    private int mOperation;

    private int mChannelId;

    private String mMerchantId;

    private String mId;

    private String mUid;

    private String mTraceNumber;

    public FollowMerchantEvent(int operation) {
        super("follow_merchant");
        mOperation = operation;
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public void setTraceNumber(String traceNumber) {
        mTraceNumber = traceNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(getName());
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "mid", mMerchantId);
        putValue(sb, "tn", mTraceNumber);
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        putValue(sb, "optype", String.valueOf(mOperation));
        return sb.toString();
    }
}
