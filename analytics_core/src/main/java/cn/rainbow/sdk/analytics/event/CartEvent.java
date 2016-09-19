package cn.rainbow.sdk.analytics.event;

/**
 * Created by bvin on 2016/9/14.
 */
public class CartEvent extends Event{

    /**添加商品到购物车*/
    public static final int OP_ADD_GOODS = 1001;
    /**删除购物车中商品*/
    public static final int OP_DELETE_GOODS = 1002;
    /**提交购物车*/
    public static final int OP_COMMIT = 1003;

    private String mMerchantId;

    private String mGoodsId;
    private String mGoodsSkuCode;
    private String mGoodsName;
    private String mGoodsImage;
    private String mGoodsPrice;
    private String mGoodsSellPrice;
    private String mGoodsCount;
    private String mCouponAmount;

    private String mId;
    private String mUid;
    private String mTraceNumber;

    private int mOperation;

    public CartEvent(int operation) {
        super("cart");
        mOperation = operation;
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

    public void setGoodsPrice(String goodsPrice) {
        mGoodsPrice = goodsPrice;
    }

    public void setGoodsSellPrice(String goodsSellPrice) {
        mGoodsSellPrice = goodsSellPrice;
    }

    public void setGoodsCount(String goodsCount) {
        mGoodsCount = goodsCount;
    }

    public void setCouponAmount(String couponAmount) {
        mCouponAmount = couponAmount;
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

    public String getMerchantId() {
        return mMerchantId;
    }

    public String getGoodsId() {
        return mGoodsId;
    }

    public String getGoodsSkuCode() {
        return mGoodsSkuCode;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public String getGoodsImage() {
        return mGoodsImage;
    }

    public String getGoodsPrice() {
        return mGoodsPrice;
    }

    public String getGoodsSellPrice() {
        return mGoodsSellPrice;
    }

    public String getGoodsCount() {
        return mGoodsCount;
    }

    public String getCouponAmount() {
        return mCouponAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(getName());
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "mid", mMerchantId);
        putValue(sb, "gid", mGoodsId);
        putValue(sb, "gsku", mGoodsSkuCode);
        putValue(sb, "gn", mGoodsName,true);
        putValue(sb, "gi", mGoodsImage, true);
        putValue(sb, "gp", mGoodsPrice);
        putValue(sb, "ga", mGoodsSellPrice);
        putValue(sb, "gc", mGoodsCount);
        putValue(sb, "ca", mCouponAmount);
        putValue(sb, "id", mId);
        putValue(sb, "uid", mUid);
        putValue(sb, "op", String.valueOf(mOperation));
        putValue(sb, "tn", mTraceNumber);
        return sb.toString();
    }
}
