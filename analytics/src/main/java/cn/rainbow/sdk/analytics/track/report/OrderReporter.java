package cn.rainbow.sdk.analytics.track.report;

import android.text.TextUtils;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class OrderReporter extends AbsEventReporter<OrderEvent>{
    
    public OrderReporter(OrderEvent event) {
        super(event);
    }

    @Override
    public void report(OrderEvent event, Callback callback) {
        StringBuilder sb = new StringBuilder(ApiConfig.URL_ORDER);
        sb.append("?");
        sb.append("rt=order&");
        sb.append(event.toString());
        for (OrderEvent.Goods goods : event.getGoodsList()) {
            sb.append(goods.toString());
        }
        if (sb.toString().endsWith("&")) {
            sb.delete(sb.toString().length() - 1, sb.toString().length());
        }
        if (!TextUtils.isEmpty(sb.toString()))//不用retrofit是因为此接口参数不固定
            mHttpLite.url(sb.toString()).get().async(new BaseResponseCallback(callback,true));
    }

    public static class Keys {
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String ORDER_NUMBER = "on";
        public static final String SUB_ORDER_NUMBER = "son";
        public static final String ORDER_STATE = "os";
        public static final String ORDER_USER = "ou";
        public static final String ORDER_PRICE = "op";
        public static final String ORDER_ADDRESS = "oa";
        public static final String COUPON_PRICE = "cp";
        public static final String FREIGHT_PRICE = "fp";
        public static final String GOODS_TOTAL = "pn";
        public static final String OPERATION_TYPE = "opt";

        public static final String GOODS_ID = "gi";
        public static final String GOODS_SKU_CODE = "gs";
        public static final String GOODS_COUNT = "gc";
        public static final String GOODS_NAME = "gn";
        public static final String GOODS_IMAGE = "gm";
    }
}
