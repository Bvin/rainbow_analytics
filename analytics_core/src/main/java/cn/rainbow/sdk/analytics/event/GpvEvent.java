package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/14.
 */
public class GpvEvent extends Event{

    private int mChannelId;
    private String mMerchantId;
    private String mGoodsId;
    private String mGoodsName;
    private String mGoodsImage;
    private String mId;
    private String mUid;
    private String mCategory1;
    private String mCategory2;
    private String mCategory3;
    private String mEnterTime;
    private String mLeaveTime;
    private String mTraceNumber;

    public GpvEvent() {
        super("gpv");
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public void setGoodsId(String goodsId) {
        mGoodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        mGoodsName = goodsName;
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = goodsImage;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public void setCategory1(String category1) {
        mCategory1 = category1;
    }

    public void setCategory2(String category2) {
        mCategory2 = category2;
    }

    public void setCategory3(String category3) {
        mCategory3 = category3;
    }

    public void setEnterTime(String enterTime) {
        mEnterTime = enterTime;
    }

    public void setLeaveTime(String leaveTime) {
        mLeaveTime = leaveTime;
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
        putValue(sb, "gid", mGoodsId);
        putValue(sb, "gn", mGoodsName, true);
        putValue(sb, "gi", mGoodsImage, true);
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        putValue(sb, "gc1", mCategory1);
        putValue(sb, "gc2", mCategory2);
        putValue(sb, "gc3", mCategory3);
        putValue(sb, "et", mEnterTime);
        putValue(sb, "lt", mLeaveTime);
        putValue(sb, "tn", mTraceNumber);
        return sb.toString();
    }

}
