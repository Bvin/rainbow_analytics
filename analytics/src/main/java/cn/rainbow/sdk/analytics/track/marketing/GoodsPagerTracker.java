package cn.rainbow.sdk.analytics.track.marketing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.marketing.GoodsViewEvent;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bvin on 2016/6/13.
 */
public class GoodsPagerTracker extends MarketingPageTracker{

    private GoodsViewEvent mEvent;

    public GoodsPagerTracker(Context context) {
        super(context);
    }

    @Override
    public void onPageStartAfter(String previousPage) {
        onEventStart();
    }

    public void startGoodsPage(GoodsViewEvent eventData){
        mEvent = eventData;
        onPageStartAfter(null);
    }

    @Override
    public void onPageEnd() {
        onEventEnd();
        reportGPV();
    }

    private void reportGPV() {
        Retrofit retrofit = RetrofitClient.getInstance();
        Api api = retrofit.create(Api.class);
        Call<Model> call = api.reportGPV(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getGoodsId(), mEvent.getGoodsName(), mEvent.getGoodsImage(),
                mEvent.getStartDate(), mEvent.getEndDate(), mEvent.getCategory1(), mEvent.getCategory2(), mEvent.getCategory3(), mEvent.getId()
                , mEvent.getUid());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Model> call, Response<Model> response) {
        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<Model> call, Throwable t) {
        super.onFailure(call, t);
    }

    @Override
    public PageEvent createEvent() {
        return mEvent;
    }

    @Override
    public SQLTable createTable(PageEvent event, SQLiteDatabase database) {
        return super.createTable(event, database);
    }
}
