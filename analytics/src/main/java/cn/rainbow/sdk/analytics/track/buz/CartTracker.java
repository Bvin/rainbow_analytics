package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bvin on 2016/6/14.
 */
public class CartTracker extends AbsEventTracker<CartEvent> implements Callback<Model> {

    public static final int EVENT_ID = 1040;

    private CartEvent mEvent;

    public CartTracker() {
        super(EVENT_ID, "购物车统计");
    }

    public void startTrack(Context context,CartEvent event){
        attachContext(context);
        mEvent = event;
        reportCart();
    }

    private void reportCart() {
        Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportCart(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getGoodsPrice(), mEvent.getGoodsSellPrice(), mEvent.getGoodsCount(), mEvent.getCouponAmount(), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation());
        call.enqueue(this);
    }

    @Override
    public CartEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(CartEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }

    @Override
    public void onResponse(Call<Model> call, Response<Model> response) {
        if (response.body() != null) {
            if (response.body().getRet() == 200) {
                Log.d("reportCart-response:", response.body().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<Model> call, Throwable t) {
        //失败应重传...
        Log.e("CartTracker", "onFailure: ", t);
    }
}
