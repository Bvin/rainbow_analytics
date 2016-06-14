package cn.rainbow.sdk.analytics.track.marketing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import cn.rainbow.sdk.analytics.event.marketing.FavoriteEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bvin on 2016/6/14.
 */
public class FavTracker extends AbsEventTracker<FavoriteEvent> implements Callback<Model> {

    public static final int EVENT_ID = 1131;

    private FavoriteEvent mEvent;

    public FavTracker() {
        super(EVENT_ID, "商品收藏统计");
    }

    public void startTrack(Context context,FavoriteEvent event){
        attachContext(context);
        mEvent = event;
        reportFav();
    }

    private void reportFav(){
        Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportFav(mEvent.getChannelId(),mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsSkuCode(), mEvent.getGoodsName()
                , mEvent.getGoodsImage(), mEvent.getId(),
                mEvent.getUid(), mEvent.getOperation());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Model> call, Response<Model> response) {
        if (response.body() != null) {
            if (response.body().getRet() == 200) {
                Log.d("reportFav-response:", response.body().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<Model> call, Throwable t) {
        //失败应重传...
        Log.e("FavTracker", "onFailure: ", t);
    }

    @Override
    public FavoriteEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(FavoriteEvent event, SQLiteDatabase database) {
        return null;
    }

    @Override
    protected void save() {
        //super.save();
    }

}
