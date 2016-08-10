package cn.rainbow.sdk.analytics.track.report;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class CartReporter extends AbsEventReporter<CartEvent>{
    
    public CartReporter(CartEvent event) {
        super(event);
    }

    @Override
    public void report(CartEvent event, Callback callback) {
        Api api = mHttpLite.retrofit(Api.class, new PreRequestListener());
        api.reportCart("cart",event.getChannelId(),
                event.getMerchantId(),
                event.getGoodsId(),
                event.getGoodsSkuCode(),
                urlEncode( event.getGoodsName()),
                urlEncode(event.getGoodsImage()),
                event.getGoodsPrice(),
                event.getGoodsSellPrice(),
                event.getGoodsCount(),
                event.getCouponAmount(),
                event.getId(),
                event.getUid(),
                event.getOperation(),
                event.getTraceNumber(),
                new BaseResponseCallback(callback));
    }

    public class Keys{
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String GOODS_ID = "gid";
        public static final String GOODS_SKU_CODE = "gsku";
        public static final String GOODS_NAME = "gn";
        public static final String GOODS_IMAGE = "gi";
        public static final String GOODS_PRICE = "gp";
        public static final String GOODS_SELL_PRICE = "ga";
        public static final String GOODS_COUNT = "gc";
        public static final String COUPON_AMOUNT = "ca";
        public static final String DEVICE_ID = "id";
        public static final String USER_ID = "uid";
        public static final String OPERATION = "op";
    }
}
