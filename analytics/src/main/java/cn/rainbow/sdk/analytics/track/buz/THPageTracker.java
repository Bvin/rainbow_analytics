package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
import cn.rainbow.sdk.analytics.track.PageTracker;
import cn.rainbow.sdk.analytics.utils.InfoCollectHelper;


/**
 * Created by bvin on 2016/6/12.
 */
public class THPageTracker extends PageTracker implements Callback<Model> {

    public static final String TH_CHANNEL = "TH_CHANNEL";
    public static final String OS = "android";

    private THPageEvent mEvent;

    public THPageTracker(Context context) {
        super(context);
    }

    @Override
    public void onPageStartAfter(String previousPage) {
        //super.onPageStartAfter(previousPage);
        if (mEvent == null) {
            mEvent = new THPageEvent();
        }
        mEvent.setUrl(mContext.getClass().getName());
        onEventStart();
        collectInfo();
    }

    @Override
    public void onPageEnd() {
        super.onPageEnd();
        reportAPV();
    }

    @Override
    protected void save() {
        //super.save();
        //暂时不存库
    }

    private void collectInfo() {
        InfoCollectHelper infoCollectHelper = new InfoCollectHelper(mContext);
        int channelId = infoCollectHelper.getMetaDataIntValue(TH_CHANNEL);
        if (channelId > 0) {
            mEvent.setChannelId(channelId);//红领巾APP
        }
        //mEvent.setMerchantId("1");//商户id，不传默认为1：天虹
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            mEvent.setAppVersion(packageInfo.versionName);
        }
        mEvent.setSystem(OS);
        mEvent.setSystemVersion(Build.VERSION.RELEASE);
        mEvent.setDevice(Build.MODEL);
        mEvent.setDeviceId(infoCollectHelper.getDeviceUUID());
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

    private void reportAPV(){
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        httpLite.setBaseUrl(ApiConfig.HOST);
        Api api = httpLite.retrofit(Api.class, new PreRequestListener());
        String pageName = mEvent.getUrl();
        try {
            pageName = URLEncoder.encode(pageName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        api.reportAPV(mEvent.getChannelId(), mEvent.getMerchantId(), pageName, mEvent.getAppVersion(), mEvent.getStartDate(),
                mEvent.getEndDate(), mEvent.getDevice(), mEvent.getSystem(), mEvent.getSystemVersion(), mEvent.getDeviceId(),new BaseResponseCallback(this));
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
    }

    @Override
    public void onFailed(Request request, Exception e) {
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
