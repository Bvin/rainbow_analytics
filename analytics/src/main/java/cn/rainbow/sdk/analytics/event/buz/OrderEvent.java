package cn.rainbow.sdk.analytics.event.buz;

import java.util.List;

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
    private String mOrderUser;
    private String mOrderPrice;
    private String mOrderAddress;
    private String mCouponPrice;
    private String mFreightPrice;
    private String mGoodsCount;
    private List<Goods> mGoodsList;

    private int mOperation;

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

    public void setOrderUser(String orderUser) {
        mOrderUser = orderUser;
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

    public String getOrderUser() {
        return mOrderUser;
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

    public class Goods{
        private String mGoodsId;
        private String mGoodsSkuCode;
        private String mGoodsCount;
        private String mGoodsName;
        private String mSkuImage;

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
    }
}
