package cn.rainbow.sdk.analytics.track.report;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class FavReporter extends AbsEventReporter<FavoriteEvent>{

    public FavReporter(FavoriteEvent event) {
        super(event);
    }

    @Override
    public void report(FavoriteEvent event, Callback callback) {
        Api api = mHttpLite.retrofit(Api.class, new PreRequestListener());
        api.reportFav(event.getChannelId(),event.getMerchantId(), event.getGoodsId(), event.getGoodsSkuCode(), urlEncode(event.getGoodsName())
                , urlEncode(event.getGoodsImage()), event.getId(),
                event.getUid(), event.getOperation(),new BaseResponseCallback(callback));
    }
}
