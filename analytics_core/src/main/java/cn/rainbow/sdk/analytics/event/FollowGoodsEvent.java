package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/18.
 */
public class FollowGoodsEvent extends Event{

    /**添加收藏*/
    public static final int OP_ADD_FAV = 2001;
    /**取消收藏*/
    public static final int OP_CANCEL_FAV = 2002;

    private int mOperation;

    private int mChannelId;

    private String mMerchantId;

    private String mGoodsId;

    private String mGoodsSkuCode;

    private String mGoodsName;

    private String mGoodsImage;

    private String mId;

    private String mUid;

    private String mTraceNumber;

    public FollowGoodsEvent(int operation) {
        super("follow_goods");
        mOperation = operation;
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

    public void setGoodsSkuCode(String goodsSkuCode) {
        mGoodsSkuCode = goodsSkuCode;
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
        putValue(sb, "gsku", mGoodsSkuCode);
        putValue(sb, "gn", mGoodsName, true);
        putValue(sb, "gi", mGoodsImage, true);
        putValue(sb, "tn", mTraceNumber);
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        putValue(sb, "op", String.valueOf(mOperation));
        return sb.toString();
    }
}
