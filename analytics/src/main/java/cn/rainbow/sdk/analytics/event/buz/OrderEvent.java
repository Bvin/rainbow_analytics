package cn.rainbow.sdk.analytics.event.buz;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.track.report.OrderReporter;

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

    private int mOperation;

    private ContentValues mValues;

    /**
     * 订单事件.
     * @param operation 操作类型
     */
    public OrderEvent(int operation) {
        mOperation = operation;
    }

    public OrderEvent(Cursor cursor) {
        initBaseColumns(cursor);
        mChannelId = cursor.getInt(cursor.getColumnIndex(OrderReporter.Keys.CHANNEL_ID));
        mMerchantId = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.MERCHANT_ID));
        mOrderNumber = cursor.getString(cursor.getColumnIndex(OrderTable.Columns.ORDER_NUMBER));
        mSubOrderNumber = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.SUB_ORDER_NUMBER));
        mOrderState = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.ORDER_STATE));
        mOrderUserId = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.ORDER_USER));
        mOrderPrice = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.ORDER_PRICE));
        mOrderAddress = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.ORDER_ADDRESS));
        mCouponPrice = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.COUPON_PRICE));
        mFreightPrice = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.FREIGHT_PRICE));
        mGoodsCount = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.GOODS_TOTAL));
        mOperation = cursor.getInt(cursor.getColumnIndex(OrderReporter.Keys.OPERATION_TYPE));
        String goodsList = cursor.getString(cursor.getColumnIndex(OrderReporter.Keys.GOODS_LIST));
        if (!TextUtils.isEmpty(goodsList)) {
            mGoodsList = parseString(goodsList);
        }
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
            if (entry.getKey().equals(OrderTable.Columns.ORDER_NUMBER)) {
                sb.append(OrderReporter.Keys.ORDER_NUMBER);//由于on是数据库表的保留字段需要转换一下
            } else {
                sb.append(entry.getKey());
            }
            sb.append("=").append(entry.getValue()).append("&");
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
            putValidInt(mValues, OrderReporter.Keys.CHANNEL_ID, mChannelId);
            putValidString(mValues, OrderReporter.Keys.MERCHANT_ID, mMerchantId);
            putValidString(mValues, OrderTable.Columns.ORDER_NUMBER, mOrderNumber);
            putValidString(mValues, OrderReporter.Keys.SUB_ORDER_NUMBER, mSubOrderNumber);
            putValidString(mValues, OrderReporter.Keys.ORDER_STATE, mOrderState);
            putValidString(mValues, OrderReporter.Keys.ORDER_USER, mOrderUserId);
            putValidString(mValues, OrderReporter.Keys.ORDER_PRICE, mOrderPrice);
            putValidString(mValues, OrderReporter.Keys.ORDER_ADDRESS, mOrderAddress);
            putValidString(mValues, OrderReporter.Keys.COUPON_PRICE, mCouponPrice);
            putValidString(mValues, OrderReporter.Keys.GOODS_TOTAL, mGoodsCount);
            putValidString(mValues, OrderReporter.Keys.FREIGHT_PRICE, mFreightPrice);
            putValidInt(mValues, OrderReporter.Keys.OPERATION_TYPE, mOperation);
            if (mGoodsList != null) {
                String goodsList = asString(mGoodsList);//序列化
                if (!TextUtils.isEmpty(goodsList)) {
                    putValidString(mValues, OrderReporter.Keys.GOODS_LIST, goodsList);
                }
            }
        }
        return mValues;
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
                    sb.append(GOODS_SEPARATOR);
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 字符串反序列化成商品列表
     * @param string 类似i[1][gi]=v1&i[1][gn]=v2&i[1][gm]=v3;i[2][gi]=v1&i[2][gn]=v2&i[2][gm]=v3
     * @return
     */
    private List<Goods> parseString(String string){
        if (!TextUtils.isEmpty(string)){
            List<Goods> list = new ArrayList<>();
            String[] goodsArray = string.split(GOODS_SEPARATOR);
            for (String goodsString : goodsArray) {
                list.add(new Goods(goodsString));
            }
            return list;
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

        public Goods(String urlString) {
            //k1=v1&k2=v2&k3=v3....
            if (!TextUtils.isEmpty(urlString) && urlString.contains("&")) {
                String[] goodsElement = urlString.split("&");
                for (int i = 0; i < goodsElement.length; i++) {
                    String query = goodsElement[i];
                    if (!TextUtils.isEmpty(query) && query.contains("=")) {
                        //k1=v1&k2=v2
                        String[] queryArray = query.split("=");//length=2
                        String key = queryArray[0];
                        if (queryArray.length > 1) {
                            String value = queryArray[1];
                            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                                int firstPreSquareBracketsIndex = key.indexOf("[") + 1;
                                int firstSufSquareBracketsIndex = key.indexOf("]");
                                int lastPreSquareBracketsIndex = key.lastIndexOf("[") + 1;
                                int lastSufSquareBracketsIndex = key.lastIndexOf("]");
                                //i[1][gi]/i[1][gn]//i[2][gn]
                                if (mIndex < 0) {//取出index
                                    String index = key.substring(firstPreSquareBracketsIndex, firstSufSquareBracketsIndex);
                                    try {
                                        mIndex = Integer.valueOf(index);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //拿到真正的key
                                key = key.substring(lastPreSquareBracketsIndex, lastSufSquareBracketsIndex);
                                if (key.equals(OrderReporter.Keys.GOODS_ID)) {
                                    mGoodsId = value;
                                } else if (key.equals(OrderReporter.Keys.GOODS_NAME)) {
                                    mGoodsName = value;
                                } else if (key.equals(OrderReporter.Keys.GOODS_SKU_CODE)) {
                                    mGoodsSkuCode = value;
                                } else if (key.equals(OrderReporter.Keys.GOODS_COUNT)) {
                                    mGoodsCount = value;
                                } else if (key.equals(OrderReporter.Keys.GOODS_IMAGE)) {
                                    mSkuImage = value;
                                }
                            }
                        }
                    }
                }
            }
        }

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
            sb.append(put(OrderReporter.Keys.GOODS_ID,mGoodsId));
            sb.append(put(OrderReporter.Keys.GOODS_NAME,mGoodsName));
            sb.append(put(OrderReporter.Keys.GOODS_SKU_CODE,mGoodsSkuCode));
            sb.append(put(OrderReporter.Keys.GOODS_COUNT,mGoodsCount));
            sb.append(put(OrderReporter.Keys.GOODS_IMAGE,mSkuImage));
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
