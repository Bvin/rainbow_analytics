package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bvin on 2016/6/14.
 */
public class OrderTracker extends AbsEventTracker<OrderEvent> implements Callback<Model> {

    public static final int EVENT_ID = 1122;

    private OrderEvent mEvent;

    public OrderTracker() {
        super(EVENT_ID, "订单统计");
    }

    public void startTrack(Context context,OrderEvent event){
        attachContext(context);
        mEvent = event;
        reportOrder();
    }

    private void reportOrder(){
        Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportOrder(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getOrderNumber(), mEvent.getSubOrderNumber(), mEvent.getOrderState()
                , mEvent.getOrderUser(), mEvent.getOrderPrice(), mEvent.getOrderAddress(), mEvent.getCouponPrice(), mEvent.getFreightPrice(), mEvent.getGoodsCount(),
                 mEvent.getOperation());
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<Model> call, Response<Model> response) {
        if (response.body() != null) {
            if (response.body().getRet() == 200) {
                Log.d("reportOrder-response:", response.body().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<Model> call, Throwable t) {
        //失败应重传...
        Log.e("OrderTracker", "onFailure: ", t);
    }

    @Override
    public OrderEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(OrderEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }
}
