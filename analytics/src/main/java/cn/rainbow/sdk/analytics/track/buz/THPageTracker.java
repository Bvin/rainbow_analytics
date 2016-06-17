package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.buz.THPageTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
import cn.rainbow.sdk.analytics.track.PageTracker;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.utils.InfoCollectHelper;


/**
 * Created by bvin on 2016/6/12.
 */
public class THPageTracker extends PageTracker implements Callback<Model> {

    public static final String OS = "android";

    private THPageEvent mEvent;
    private THPageTable mTable;

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
        collectExtraInfo();//收集附加信息
    }

    @Override
    public void onPageEnd() {
        onEventEnd();
    }

    @Override
    protected void push() {
        new ApvReporter(mEvent).push(this);
    }

    private void collectExtraInfo() {

        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());//红领巾APP
        //mEvent.setMerchantId("1");//商户id，不传默认为1：天虹
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            mEvent.setAppVersion(packageInfo.versionName);
        }
        mEvent.setSystem(OS);
        mEvent.setSystemVersion(Build.VERSION.RELEASE);
        mEvent.setDevice(Build.MODEL);
        mEvent.setDeviceId(new InfoCollectHelper(mContext).getDeviceUUID());
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
        if (mTable == null) {
            mTable = new THPageTable(database);
        }
        return mTable;
    }

}
