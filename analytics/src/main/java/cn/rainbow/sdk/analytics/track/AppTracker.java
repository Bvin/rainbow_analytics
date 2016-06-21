package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.DisplayMetrics;

import cn.rainbow.sdk.analytics.BuildConfig;
import cn.rainbow.sdk.analytics.data.local.db.table.AppTable;
import cn.rainbow.sdk.analytics.data.local.db.DBHelper;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.AppEvent;

/**
 * Created by 32967 on 2016/5/31.
 * 负责App初始化跟踪统计.
 */
public class AppTracker extends AbsEventTracker<AppEvent>{

    private int mAppId;
    private AppEvent mAppEvent;
    private AppTable mAppTable;

    public AppTracker(Context context,int appId) {
        super(context,AppEvent.EVENT_ID, "App级统计");
        mAppId = appId;
    }

    public void onStart(){
        if (mAppEvent == null) {
            mAppEvent = new AppEvent(mAppId);//从配置读取APP_ID
        }
        if (mAppTable == null) {
            mAppTable = new AppTable(mContext);
        }
        collectInfo();
        onEventStart();
        mAppTable.insert(mAppEvent);//开始就统计
    }

    private void collectInfo() {
        mAppEvent.setAppName(mContext.getPackageManager().getApplicationLabel(mContext.getApplicationInfo()).toString());
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            mAppEvent.setAppVersion(packageInfo.versionCode);
            mAppEvent.setAppVersionName(packageInfo.versionName);
        }
        mAppEvent.setBuildType(BuildConfig.BUILD_TYPE);
        mAppEvent.setBuildVariant(BuildConfig.FLAVOR);
        mAppEvent.setSystemVersion(Build.VERSION.SDK_INT);
        mAppEvent.setDeviceModel(Build.MODEL);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        mAppEvent.setScreenSize(new int[]{dm.widthPixels, dm.heightPixels});
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

    public void onExit() throws IllegalStateException{
        onEventEnd();//will call bellow save() method
    }

    @Override
    protected void save() {
        mAppTable.update(mAppEvent);//convert save to update.
    }

    @Override
    public AppEvent takeEvent() {
        return mAppEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mAppTable == null) {
            mAppTable = new AppTable(mContext);
        }
        return mAppTable;
    }
}
