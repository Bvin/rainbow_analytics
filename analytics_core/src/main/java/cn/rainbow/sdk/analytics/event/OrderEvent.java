package cn.rainbow.sdk.analytics.event;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by bvin on 2016/9/18.
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

    private static final String GOODS_SEPARATOR = ";";

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
    private String mTraceNumber;
    private int mOperation;

    public OrderEvent(int operation) {
        super("order");
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

    public void setTraceNumber(String traceNumber) {
        mTraceNumber = traceNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(getName());
        putValue(sb, "op", String.valueOf(mOperation));
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "mid", mMerchantId);
        putValue(sb, "on", mOrderNumber);
        putValue(sb, "son", mSubOrderNumber);
        putValue(sb, "os", mOrderState);
        putValue(sb, "ou", mOrderUserId);
        putValue(sb, "op", mOrderPrice);
        putValue(sb, "oa", mOrderAddress, true);
        putValue(sb, "cp", mCouponPrice);
        putValue(sb, "fp", mFreightPrice);
        putValue(sb, "pn", mGoodsCount);
        if (mGoodsList != null) {
            String goodsList = asString(mGoodsList);//序列化
            if (!TextUtils.isEmpty(goodsList)) {
                sb.append("&").append(goodsList);
            }
        }
        putValue(sb, "tn", mTraceNumber);
        if (sb.toString().endsWith("&")) {
            sb.delete(sb.toString().length() - 1, sb.toString().length());
        }
        return sb.toString();
    }

    /**
     * 商品列表序列化成字符串.
     * @param list 商品列表
     * @return 以GOODS_SEPARATOR(;)分割的字符串
     */
    private String asString(List<Goods> list) {
        if (list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString());//remove last &
                if (i != list.size() - 1) {
                    sb.append("&");
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static class Goods{
        private String mGoodsId;
        private String mGoodsSkuCode;
        private String mGoodsCount;
        private String mGoodsName;
        private String mSkuImage;

        private int mIndex = -1;//默认-1

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

        /**
         * 设置商品名称（将会进行url转码）
         * @param goodsName
         */
        public void setGoodsName(String goodsName) {
            mGoodsName = urlEncode(goodsName);
        }

        /**
         * 设置SKU图片（将会进行url转码）
         * @param skuImage
         */
        public void setSkuImage(String skuImage) {
            mSkuImage = urlEncode(skuImage);
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
            sb.append(put("gi",mGoodsId));
            sb.append(put("gn",mGoodsName));
            sb.append(put("gs",mGoodsSkuCode));
            sb.append(put("gc",mGoodsCount));
            sb.append(put("gm",mSkuImage));
            return sb.toString();
        }

        private String put(String key,String value){
            if (!TextUtils.isEmpty(value)){
                return "i["+mIndex+"]["+key+"]="+value+"&";
            }
            return "";
        }

        /**
         * URL编码.
         * @param content 内容
         * @return 成功返回URL编码后的内容，否则返元原来内容
         */
        protected String urlEncode(String content){
            if (TextUtils.isEmpty(content)) return content;
            try {
                content = URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return content;
        }
    }
}
