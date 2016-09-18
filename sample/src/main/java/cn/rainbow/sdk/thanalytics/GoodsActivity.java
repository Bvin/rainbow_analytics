package cn.rainbow.sdk.thanalytics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.event.CartEvent;
import cn.rainbow.sdk.analytics.event.FollowGoodsEvent;
import cn.rainbow.sdk.analytics.event.GpvEvent;
import cn.rainbow.sdk.analytics.event.OrderEvent;
import cn.rainbow.sdk.analytics.event.THEvent;

public class GoodsActivity extends BaseActivity {

    private GpvEvent mGoodsViewEventData;

    public static void start(Context context) {
        Intent starter = new Intent(context, GoodsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        mGoodsViewEventData = new GpvEvent();
        mGoodsViewEventData.setGoodsId("007");
        mGoodsViewEventData.setGoodsName("商品名称");
        mGoodsViewEventData.setGoodsImage("url");
        mGoodsViewEventData.setTraceNumber("tn");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoodsViewEventData.setEnterTime(getCurrentDate());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsViewEventData.setLeaveTime(getCurrentDate());
        THAnalytics.track(mGoodsViewEventData);
    }

    public void trackCart(View view){
        for (int i = 0; i < 1; i++) {
            trackCartEvent();
        }
    }

    private void trackCartEvent() {
        CartEvent cartEvent = new CartEvent(CartEvent.OP_ADD_GOODS);
        cartEvent.setGoodsImage("goodsImage");
        cartEvent.setGoodsName("goodsName");
        THAnalytics.track(cartEvent);
    }

    public void trackOrder(View view){
        for (int i = 0; i < 1; i++) {
            trackOrder();
        }
    }

    private void trackOrder() {
        OrderEvent orderEvent = new OrderEvent(OrderEvent.OP_COMMIT);
        orderEvent.setOrderAddress("order address");
        orderEvent.setOrderPrice("121");
        orderEvent.setOrderNumber("#4165434");
        List<OrderEvent.Goods> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderEvent.Goods goods = new OrderEvent.Goods(i);
            goods.setGoodsSkuCode("sku-"+i);
            goods.setGoodsName("gn");
            goods.setGoodsId("gi");
            goods.setGoodsCount(""+i);
            list.add(goods);
        }
        orderEvent.setGoodsList(list);
        THAnalytics.track(orderEvent);
    }

    public void trackFav(View view){
        for (int i = 0; i < 1; i++) {
            trackFavEvent();
        }
    }

    private void trackFavEvent() {
        FollowGoodsEvent favoriteEvent = new FollowGoodsEvent(FollowGoodsEvent.OP_ADD_FAV);
        favoriteEvent.setGoodsName("goods name");
        favoriteEvent.setGoodsId("65454");
        favoriteEvent.setGoodsImage("agsdhsasfg");
        THAnalytics.track(favoriteEvent);
    }

    public void trackEvent(View view){
        for (int i = 0; i < 1; i++) {
            trackEvent();
        }
    }

    private void trackEvent() {
        THEvent thEvent = new THEvent(THEvent.EVENT_ID_CLICK);
        thEvent.setUrl("GoodsActivity");
        thEvent.setLink("link");
        thEvent.setTraceNumber("test");
        thEvent.setElementTraceNumber("etn");
        THAnalytics.track(thEvent);
    }

    @Override
    public String traceNumber() {
        return "GoodsActivity";
    }
}
