package cn.rainbow.sdk.thanalytics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.event.marketing.GoodsViewEvent;

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
}
