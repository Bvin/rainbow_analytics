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
        api.reportCart(event.getChannelId(), event.getMerchantId(), event.getGoodsId(), event.getGoodsSkuCode(),urlEncode( event.getGoodsName())
                , urlEncode(event.getGoodsImage()), event.getGoodsPrice(), event.getGoodsSellPrice(), event.getGoodsCount(), event.getCouponAmount(), event.getId(),
                event.getUid(), event.getOperation(),new BaseResponseCallback(callback));
    }
}
