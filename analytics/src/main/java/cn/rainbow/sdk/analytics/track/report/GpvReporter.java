package cn.rainbow.sdk.analytics.track.report;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class GpvReporter extends AbsEventReporter<GoodsViewEvent>{

    public GpvReporter(GoodsViewEvent event) {
        super(event);
    }

    @Override
    public void report(GoodsViewEvent event, Callback callback) {
        Api api = mHttpLite.retrofit(Api.class, new PreRequestListener());
        api.reportGPV(event.getChannelId(), event.getMerchantId(), event.getGoodsId(), urlEncode(event.getGoodsName()), urlEncode(event.getGoodsImage()),
                event.getStartDate(), event.getEndDate(), event.getCategory1(), event.getCategory2(), event.getCategory3(), event.getId()
                , event.getUid(),new BaseResponseCallback(callback));
    }
}
