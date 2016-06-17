package cn.rainbow.sdk.analytics.event.buz;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/14.
 * <p>商品收藏事件.
 */
public class FavoriteEvent extends Event{

    /**添加收藏*/
    public static final int OP_ADD_FAV = 2001;
    /**取消收藏*/
    public static final int OP_CANCEL_FAV = 2002;

    private int mChannelId;
    private String mMerchantId;

    private String mGoodsId;
    private String mGoodsSkuCode;
    private String mGoodsName;
    private String mGoodsImage;

    private String mId;
    private String mUid;

    private int mOperation;

    /**
     * 商品收藏事件.
     * @param operation 操作类型
     */
    public FavoriteEvent(int operation) {
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
