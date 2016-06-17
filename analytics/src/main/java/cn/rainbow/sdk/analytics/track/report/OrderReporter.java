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
}
