package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;

import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;

/**
 * Created by bvin on 2016/6/13.
 * 业务接口.
 */
public interface Business {
    void startGoodsPage(Context context, GoodsViewEvent eventData);
    void stopGoodsPage();

    void trackCart(Context context, CartEvent eventData);
    void trackFav(Context context, FavoriteEvent eventData);
    void trackOrder(Context context, OrderEvent eventData);
}
