package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THPageTable;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
import cn.rainbow.sdk.analytics.track.PageTracker;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.utils.InfoCollectHelper;


/**
 * Created by bvin on 2016/6/12.
 */
public class THPageTracker extends PageTracker{

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

    public void update(THPageEvent event){
        mEvent = event;
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
    public PageEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new THPageTable(mContext);
        }
        return mTable;
    }

}
