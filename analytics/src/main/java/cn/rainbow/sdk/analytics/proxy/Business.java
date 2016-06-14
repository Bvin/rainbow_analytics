package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;

import cn.rainbow.sdk.analytics.event.marketing.CartEvent;
import cn.rainbow.sdk.analytics.event.marketing.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.marketing.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.marketing.OrderEvent;

/**
 * Created by bvin on 2016/6/13.
 */
public interface Business {
    void startGoodsPage(Context context, GoodsViewEvent eventData);
    void stopGoodsPage();

    void trackCart(Context context, CartEvent eventData);
    void trackFav(Context context, FavoriteEvent eventData);
    void trackOrder(Context context, OrderEvent eventData);
}
