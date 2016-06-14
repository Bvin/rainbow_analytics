package cn.rainbow.sdk.thanalytics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;

public class GoodsActivity extends BaseActivity {

    private  GoodsViewEvent mGoodsViewEventData;

    public static void start(Context context) {
        Intent starter = new Intent(context, GoodsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        mGoodsViewEventData = new GoodsViewEvent();
        mGoodsViewEventData.setGoodsId("007");
        mGoodsViewEventData.setGoodsName("商品名称");
        mGoodsViewEventData.setGoodsImage("url");
    }

    @Override
    protected void onResume() {
        super.onResume();
        THAnalytics.startGoodsPage(this,mGoodsViewEventData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        THAnalytics.stopGoodsPage();
    }

    public void trackCart(View view){
        CartEvent cartEvent = new CartEvent(CartEvent.OP_ADD_GOODS);
        cartEvent.setGoodsImage("goodsImage");
        cartEvent.setGoodsName("goodsName");
        THAnalytics.trackCart(this,cartEvent);
    }

    public void trackOrder(View view){
        OrderEvent orderEvent = new OrderEvent(OrderEvent.OP_COMMIT);
        orderEvent.setOrderAddress("order address");
        orderEvent.setOrderPrice("121");
        orderEvent.setOrderNumber("#4165434");
        List<OrderEvent.Goods> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderEvent.Goods goods = new OrderEvent.Goods(i);
            goods.setGoodsName("gn");
            goods.setGoodsId("gi");
            goods.setGoodsCount(""+i);
            list.add(goods);
        }
        orderEvent.setGoodsList(list);
        THAnalytics.trackOrder(this,orderEvent);
    }

    public void trackFav(View view){
        FavoriteEvent favoriteEvent = new FavoriteEvent(FavoriteEvent.OP_ADD_FAV);
        favoriteEvent.setGoodsName("goods name");
        favoriteEvent.setGoodsId("65454");
        favoriteEvent.setGoodsImage("agsdhsasfg");
        THAnalytics.trackFavorate(this,favoriteEvent);
    }
}
