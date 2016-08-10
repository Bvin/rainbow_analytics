package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.database.Cursor;

import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/13.
 * <p>商品浏览事件.
 */
public class GoodsViewEvent extends THPageEvent {

    private String mGoodsId;
    private String mGoodsName;
    private String mGoodsImage;

    private String mId;
    private String mUid;

    private String mCategory1;
    private String mCategory2;
    private String mCategory3;
    private ContentValues mValues;

    public GoodsViewEvent() {
    }

    public GoodsViewEvent(Cursor cursor) {
        if (cursor != null) {
            initBaseColumns(cursor);
            mChannelId = cursor.getInt(cursor.getColumnIndex(GpvReporter.Keys.CHANNEL_ID));
            mMerchantId = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.MERCHANT_ID));
            mGoodsId = cursor.getString(cursor.getColumnIndex(GoodsTable.Columns.GOODS_ID));
            mGoodsName = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.GOODS_NAME));
            mGoodsImage = cursor.getString(cursor.getColumnIndex(GoodsTable.Columns.GOODS_IMAGE));
            mCategory1 = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.GOODS_CATEGORY1));
            mCategory2 = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.GOODS_CATEGORY2));
            mCategory3 = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.GOODS_CATEGORY3));
            mStartDate = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.ENTER_TIME));
            mEndDate = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.LEAVE_TIME));
            mId = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.DEVICE_ID));
            mUid = cursor.getString(cursor.getColumnIndex(GpvReporter.Keys.USER_ID));
            setTraceNumber(cursor.getString(cursor.getColumnIndex(ApvReporter.Keys.TRACE_NUMBER)));
        }
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

    @Override
    public ContentValues saveValues() {
        if (mValues == null) {
            mValues = new ContentValues();
            putValidInt(mValues, GpvReporter.Keys.CHANNEL_ID, mChannelId);
            putValidString(mValues, GpvReporter.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, GoodsTable.Columns.GOODS_ID, mGoodsId);
            putValidString(mValues, GpvReporter.Keys.ENTER_TIME, mStartDate);
            putValidString(mValues, GpvReporter.Keys.LEAVE_TIME, mEndDate);
            putValidString(mValues, GpvReporter.Keys.GOODS_NAME, mGoodsName);
            putValidString(mValues, GoodsTable.Columns.GOODS_IMAGE, mGoodsImage);
            putValidString(mValues, GpvReporter.Keys.GOODS_CATEGORY1, mCategory1);
            putValidString(mValues, GpvReporter.Keys.GOODS_CATEGORY2, mCategory2);
            putValidString(mValues, GpvReporter.Keys.GOODS_CATEGORY3, mCategory3);
            putValidString(mValues, GpvReporter.Keys.USER_ID, mUid);
            putValidString(mValues, GpvReporter.Keys.DEVICE_ID, mId);
            putValidString(mValues, ApvReporter.Keys.TRACE_NUMBER, getTraceNumber());
        }
        return mValues;
    }

    public String getGoodsId() {
        return mGoodsId;
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

    public String getCategory1() {
        return mCategory1;
    }

    public String getCategory2() {
        return mCategory2;
    }

    public String getCategory3() {
        return mCategory3;
    }
}
