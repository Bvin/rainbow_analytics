package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.DisplayMetrics;

import cn.rainbow.sdk.analytics.BuildConfig;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Api;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.RetrofitClient;
import cn.rainbow.sdk.analytics.event.MarketingPageEvent;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.utils.InfoCollectHelper;
import cn.rainbow.sdk.analytics.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by bvin on 2016/6/12.
 */
public class MarketingPageTracker extends PageTracker implements Callback<Model> {

    private MarketingPageEvent mEvent;

    public MarketingPageTracker(Context context) {
        super(context);
    }

    @Override
    public void onPageStartAfter(String previousPage) {
        //super.onPageStartAfter(previousPage);
        if (mEvent == null) {
            mEvent = new MarketingPageEvent();
        }
        mEvent.setUrl(mContext.getClass().getSimpleName());
        onEventStart();
        collectInfo();
    }

    @Override
    public void onPageEnd() {
        super.onPageEnd();
        reportAPV();
    }

    private void collectInfo() {
        mEvent.setChannelId("1");//红领巾APP
        mEvent.setMerchantId("1");//商户id，不传默认为1：天虹
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            mEvent.setAppVersion(packageInfo.versionName);
        }
        mEvent.setSystem("android");
        mEvent.setSystemVersion(Build.VERSION.RELEASE);
        mEvent.setDevice(Build.MODEL);
        mEvent.setDeviceId(new InfoCollectHelper(mContext).getDeviceID());
    }

    private PackageInfo getPackageInfo() {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pi;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void reportAPV() {
        Retrofit retrofit = RetrofitClient.getInstance();
        Api mApi = retrofit.create(Api.class);
        Call<Model> call = mApi.reportAPV(mEvent.getChannelId(), mEvent.getMerchantId(), mEvent.getUrl(), mEvent.getAppVersion(), mEvent.getStartDate(),
                mEvent.getEndDate(), mEvent.getDevice(), mEvent.getSystem(), mEvent.getSystemVersion(), mEvent.getDeviceId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Model> call, Response<Model> response) {
        if (response.body() != null) {
            if (response.body().getRet() == 200) {
                Log.d("reportAPV-response:", response.body().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<Model> call, Throwable t) {
        //失败应重传...
        Log.e("MarketingPageTracker", "onFailure: ", t);
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
