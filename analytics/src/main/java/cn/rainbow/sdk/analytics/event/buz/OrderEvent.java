package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import cn.rainbow.sdk.analytics.data.local.db.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.OrderTable;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/14.
 * <p>订单事件.
 */
public class OrderEvent extends Event{

    /**提交订单*/
    public static final int OP_COMMIT = 3001;
    /**订单支付*/
    public static final int OP_PAY = 3002;
    /**订单支付完成*/
    public static final int OP_PAY_COMPLETE = 3003;
    /**订单取消*/
    public static final int OP_CANCEL = 3004;
    /**订单退货*/
    public static final int OP_RETURN_GOODS = 3005;
    /**订单退款*/
    public static final int OP_REFUND = 3006;

    private int mChannelId;
    private String mMerchantId;

    private String mOrderNumber;
    private String mSubOrderNumber;
    private String mOrderState;
    private String mOrderUserId;
    private String mOrderPrice;
    private String mOrderAddress;
    private String mCouponPrice;
    private String mFreightPrice;
    private String mGoodsCount;
    private List<Goods> mGoodsList;

    private int mOperation;

    private ContentValues mValues;

    /**
     * 订单事件.
     * @param operation 操作类型
     */
    public OrderEvent(int operation) {
        mOperation = operation;
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public void setOrderNumber(String orderNumber) {
        mOrderNumber = orderNumber;
    }

    public void setSubOrderNumber(String subOrderNumber) {
        mSubOrderNumber = subOrderNumber;
    }

    public void setOrderState(String orderState) {
        mOrderState = orderState;
    }

    public void setOrderUserId(String orderUserId) {
        mOrderUserId = orderUserId;
    }

    public void setOrderPrice(String orderPrice) {
        mOrderPrice = orderPrice;
    }

    public void setOrderAddress(String orderAddress) {
        mOrderAddress = orderAddress;
    }

    public void setCouponPrice(String couponPrice) {
        mCouponPrice = couponPrice;
    }

    public void setFreightPrice(String freightPrice) {
        mFreightPrice = freightPrice;
    }

    public void setGoodsCount(String goodsCount) {
        mGoodsCount = goodsCount;
    }

    public void setGoodsList(List<Goods> goodsList) {
        mGoodsList = goodsList;
    }

    public int getChannelId() {
        return mChannelId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public String getSubOrderNumber() {
        return mSubOrderNumber;
    }

    public String getOrderState() {
        return mOrderState;
    }

    public String getOrderUserId() {
        return mOrderUserId;
    }

    public String getOrderPrice() {
        return mOrderPrice;
    }

    public String getOrderAddress() {
        return mOrderAddress;
    }

    public String getCouponPrice() {
        return mCouponPrice;
    }

    public String getFreightPrice() {
        return mFreightPrice;
    }

    public String getGoodsCount() {
        return mGoodsCount;
    }

    public List<Goods> getGoodsList() {
        return mGoodsList;
    }

    public int getOperation() {
        return mOperation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ContentValues cv = saveValues();
        for (Map.Entry<String, Object> entry : cv.valueSet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        /*if (sb.toString().endsWith("&")) {
            return sb.substring(sb.toString().length() - 1, sb.toString().length());
        }*/
        return sb.toString();
    }

    @Override
    public ContentValues saveValues() {
        if (mValues == null) {
            mValues = new ContentValues();
            putValidInt(mValues, OrderTable.Keys.CHANNEL_ID, mChannelId);
            putValidString(mValues, OrderTable.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, OrderTable.Keys.ORDER_NUMBER, mOrderNumber);
            putValidString(mValues, OrderTable.Keys.SUB_ORDER_NUMBER, mSubOrderNumber);
            putValidString(mValues, OrderTable.Keys.ORDER_STATE, mOrderState);
            putValidString(mValues, OrderTable.Keys.ORDER_USER, mOrderUserId);
            putValidString(mValues, OrderTable.Keys.ORDER_PRICE, mOrderPrice);
            putValidString(mValues, OrderTable.Keys.ORDER_ADDRESS, mOrderAddress);
            putValidString(mValues, OrderTable.Keys.COUPON_PRICE, mCouponPrice);
            putValidString(mValues, OrderTable.Keys.GOODS_COUNT, mGoodsCount);
            putValidString(mValues, OrderTable.Keys.FREIGHT_PRICE, mFreightPrice);
            putValidInt(mValues, OrderTable.Keys.OPERATION_TYPE, mOperation);
        }
        return mValues;
    }

    public static class Goods{
        private String mGoodsId;
        private String mGoodsSkuCode;
        private String mGoodsCount;
        private String mGoodsName;
        private String mSkuImage;

        private int mIndex;

        public Goods(int index) {
            mIndex = index;
        }

        public void setGoodsId(String goodsId) {
            mGoodsId = goodsId;
        }

        public void setGoodsSkuCode(String goodsSkuCode) {
            mGoodsSkuCode = goodsSkuCode;
        }

        public void setGoodsCount(String goodsCount) {
            mGoodsCount = goodsCount;
        }

        public void setGoodsName(String goodsName) {
            mGoodsName = goodsName;
        }

        public void setSkuImage(String skuImage) {
            mSkuImage = skuImage;
        }

        public String getGoodsId() {
            return mGoodsId;
        }

        public String getGoodsSkuCode() {
            return mGoodsSkuCode;
        }

        public String getGoodsCount() {
            return mGoodsCount;
        }

        public String getGoodsName() {
            return mGoodsName;
        }

        public String getSkuImage() {
            return mSkuImage;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(put(GoodsTable.Keys.GOODS_ID,mGoodsId));
            sb.append(put(GoodsTable.Keys.GOODS_NAME,mGoodsName));
            sb.append(put(GoodsTable.Keys.GOODS_SKU_CODE,mGoodsSkuCode));
            sb.append(put(GoodsTable.Keys.GOODS_COUNT,mGoodsCount));
            sb.append(put(GoodsTable.Keys.GOODS_IMAGE,mSkuImage));
            return sb.toString();
        }

        private String put(String key,String value){
            if (!TextUtils.isEmpty(value)){
                return "i["+mIndex+"]["+key+"]="+value+"&";
            }
            return "";
        }
    }
}
