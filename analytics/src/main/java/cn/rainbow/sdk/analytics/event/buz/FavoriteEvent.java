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
import cn.rainbow.sdk.analytics.track.report.FavReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/14.
 * <p>商品收藏事件.
 */
public class FavoriteEvent extends Event{

    /**添加收藏*/
    public static final int OP_ADD_FAV = 2001;
    /**取消收藏*/
    public static final int OP_CANCEL_FAV = 2002;

    @HttpParam("type")
    private String mType = "follow_goods";

    @HttpParam(FavReporter.Keys.CHANNEL_ID)
    private int mChannelId;

    @HttpParam(FavReporter.Keys.MERCHANT_ID)
    private String mMerchantId;

    @HttpParam(FavReporter.Keys.GOODS_ID)
    private String mGoodsId;

    @HttpParam(FavReporter.Keys.GOODS_SKU_CODE)
    private String mGoodsSkuCode;

    @HttpParam(FavReporter.Keys.GOODS_NAME)
    private String mGoodsName;

    @HttpParam(FavReporter.Keys.GOODS_IMAGE)
    private String mGoodsImage;

    @HttpParam(FavReporter.Keys.DEVICE_ID)
    private String mId;

    @HttpParam(FavReporter.Keys.USER_ID)
    private String mUid;

    @HttpParam(ApvReporter.Keys.TRACE_NUMBER)
    private String mTraceNumber;

    @HttpParam(FavReporter.Keys.OPERATION)
    private int mOperation;

    @NonHttpParam
    private ContentValues mValues;

    public FavoriteEvent(Cursor cursor) {
        if (cursor != null) {
            initBaseColumns(cursor);
            mOperation = cursor.getInt(cursor.getColumnIndex(FavReporter.Keys.OPERATION));
            mChannelId = cursor.getInt(cursor.getColumnIndex(FavReporter.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.MERCHANT_ID));
            mGoodsId = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.GOODS_ID));
            mGoodsName = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.GOODS_NAME));
            mGoodsImage = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.GOODS_IMAGE));
            mGoodsSkuCode = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.GOODS_SKU_CODE));
            mId = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.DEVICE_ID));
            mUid = cursor.getString(cursor.getColumnIndex(FavReporter.Keys.USER_ID));
            mTraceNumber = cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.TRACE_NUMBER));
        }
    }

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
        mGoodsName = urlEncode(goodsName);
    }

    public void setGoodsImage(String goodsImage) {
        mGoodsImage = urlEncode(goodsImage);
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
            putValidInt(mValues, FavReporter.Keys.CHANNEL_ID, mChannelId);
            putValidInt(mValues, FavReporter.Keys.OPERATION, mOperation);
            putValidString(mValues, FavReporter.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, FavReporter.Keys.GOODS_ID, mGoodsId);
            putValidString(mValues, FavReporter.Keys.GOODS_NAME, mGoodsName);
            putValidString(mValues, FavReporter.Keys.GOODS_IMAGE, mGoodsImage);
            putValidString(mValues, FavReporter.Keys.GOODS_SKU_CODE, mGoodsSkuCode);
            putValidString(mValues, FavReporter.Keys.USER_ID, mUid);
            putValidString(mValues, FavReporter.Keys.DEVICE_ID, mId);
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
