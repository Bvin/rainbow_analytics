package cn.rainbow.sdk.analytics.event.buz;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/14.
 * <p>购物车事件.
 */
public class CartEvent extends Event{

    /**添加商品到购物车*/
    public static final int OP_ADD_GOODS = 1001;
    /**删除购物车中商品*/
    public static final int OP_DELETE_GOODS = 1002;
    /**提交购物车*/
    public static final int OP_COMMIT = 1003;

    private int mChannelId;
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

    private int mOperation;

    /**
     * 购物车事件.
     * @param operation 操作类型
     */
    public CartEvent(int operation) {
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


    public int getChannelId() {
        return mChannelId;
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

    public String getId() {
        return mId;
    }

    public String getUid() {
        return mUid;
    }

    public int getOperation() {
        return mOperation;
    }
}
