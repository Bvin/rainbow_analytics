package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;

import cn.rainbow.sdk.analytics.event.marketing.GoodsViewEvent;

/**
 * Created by bvin on 2016/6/13.
 */
public interface Business {
    void startGoodsPage(Context context, GoodsViewEvent eventData);
    void stopGoodsPage();

}
