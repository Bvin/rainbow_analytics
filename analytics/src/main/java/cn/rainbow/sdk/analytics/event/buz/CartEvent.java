package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.database.Cursor;

import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.NonHttpParam;

import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.CartReporter;

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

    @HttpParam("type")
    private String mType = "cart";

    @HttpParam(CartReporter.Keys.CHANNEL_ID)
    private int mChannelId;
    @HttpParam(CartReporter.Keys.MERCHANT_ID)
    private String mMerchantId;

    @HttpParam(CartReporter.Keys.GOODS_ID)
    private String mGoodsId;
    @HttpParam(CartReporter.Keys.GOODS_SKU_CODE)
    private String mGoodsSkuCode;
    @HttpParam(CartReporter.Keys.GOODS_NAME)
    private String mGoodsName;
    @HttpParam(CartReporter.Keys.GOODS_IMAGE)
    private String mGoodsImage;
    @HttpParam(CartReporter.Keys.GOODS_PRICE)
    private String mGoodsPrice;
    @HttpParam(CartReporter.Keys.GOODS_SELL_PRICE)
    private String mGoodsSellPrice;
    @HttpParam(CartReporter.Keys.GOODS_COUNT)
    private String mGoodsCount;
    @HttpParam(CartReporter.Keys.COUPON_AMOUNT)
    private String mCouponAmount;

    @HttpParam(CartReporter.Keys.DEVICE_ID)
    private String mId;
    @HttpParam(CartReporter.Keys.USER_ID)
    private String mUid;
    @HttpParam(ApvReporter.Keys.TRACE_NUMBER)
    private String mTraceNumber;

    @HttpParam(CartReporter.Keys.OPERATION)
    private int mOperation;

    @NonHttpParam
    private ContentValues mValues;

    /**
     * 购物车事件.
     * @param operation 操作类型
     */
    public CartEvent(int operation) {
        mOperation = operation;
    }

    public CartEvent(Cursor cursor) {
        if (cursor != null) {
            initBaseColumns(cursor);
            mOperation = cursor.getInt(cursor.getColumnIndex(CartReporter.Keys.OPERATION));
            mChannelId = cursor.getInt(cursor.getColumnIndex(CartReporter.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.MERCHANT_ID));
            mGoodsId = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_ID));
            mGoodsName = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_NAME));
            mGoodsImage = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_IMAGE));
            mGoodsSkuCode = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_SKU_CODE));
            mGoodsPrice = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_PRICE));
            mGoodsSellPrice = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_SELL_PRICE));
            mGoodsCount = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.GOODS_COUNT));
            mCouponAmount = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.COUPON_AMOUNT));
            mId = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.DEVICE_ID));
            mUid = cursor.getString(cursor.getColumnIndex(CartReporter.Keys.USER_ID));
            mTraceNumber = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.TRACE_NUMBER));
        }
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
        mGoodsName = urlEncode(goodsName);
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = urlEncode(goodsImage);
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

    @Override
    public ContentValues saveValues() {
        if (mValues == null) {
            mValues = new ContentValues();
            putValidInt(mValues, CartReporter.Keys.CHANNEL_ID, mChannelId);
            putValidInt(mValues, CartReporter.Keys.OPERATION, mOperation);
            putValidString(mValues, CartReporter.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, CartReporter.Keys.GOODS_ID, mGoodsId);
            putValidString(mValues, CartReporter.Keys.GOODS_NAME, mGoodsName);
            putValidString(mValues, CartReporter.Keys.GOODS_IMAGE, mGoodsImage);
            putValidString(mValues, CartReporter.Keys.GOODS_SKU_CODE, mGoodsSkuCode);
            putValidString(mValues, CartReporter.Keys.GOODS_PRICE, mGoodsPrice);
            putValidString(mValues, CartReporter.Keys.GOODS_SELL_PRICE, mGoodsSellPrice);
            putValidString(mValues, CartReporter.Keys.GOODS_COUNT, mGoodsCount);
            putValidString(mValues, CartReporter.Keys.COUPON_AMOUNT, mCouponAmount);
            putValidString(mValues, CartReporter.Keys.USER_ID, mUid);
            putValidString(mValues, CartReporter.Keys.DEVICE_ID, mId);
            putValidString(mValues, ApvReporter.Keys.TRACE_NUMBER, mTraceNumber);
        }
        return mValues;
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

    public String getTraceNumber() {
        return mTraceNumber;
    }
}
