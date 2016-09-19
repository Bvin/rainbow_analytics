package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/18.
 */
public class FollowMerchantEvent extends Event{

    /**添加收藏*/
    public static final int OP_ADD = 3001;
    /**取消收藏*/
    public static final int OP_CANCEL = 3002;


    private int mOperation;

    private String mMerchantId;

    private String mId;

    private String mUid;

    private String mTraceNumber;

    public FollowMerchantEvent(int operation) {
        super("follow_merchant");
        mOperation = operation;
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
        putValue(sb, "optype", String.valueOf(mOperation));
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        putValue(sb, "tn", mTraceNumber);
        return sb.toString();
    }
}
