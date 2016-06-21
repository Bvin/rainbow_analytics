package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.DisplayMetrics;

import cn.rainbow.sdk.analytics.BuildConfig;
import cn.rainbow.sdk.analytics.data.local.db.table.CrashTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.CrashEvent;

/**
 * Created by bvin on 2016/6/6.
 */
public class CrashTracker extends AbsEventTracker<CrashEvent>{

    private CrashEvent mCrashEvent;
    private SQLTable mTable;

    public CrashTracker(Context context) {
        super(context, CrashEvent.EVENT_ID, CrashEvent.EVENT_NAME);
    }

    public void onCrash(String log){
        if (mCrashEvent == null) {//// FIXME: 2016/6/6 inn 导致 super.onEventStart()里抛出RunTime异常，
            // 最后在UncaughtExceptionHandler里就会导致ANR
            mCrashEvent = new CrashEvent(log);
        }
        onEventStart();
        collectInfo();
        save();
    }

    private void collectInfo() {
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            mCrashEvent.setAppVersion(packageInfo.versionCode);
            mCrashEvent.setAppVersionName(packageInfo.versionName);
        }
        mCrashEvent.setBuildType(BuildConfig.BUILD_TYPE);
        mCrashEvent.setBuildVariant(BuildConfig.FLAVOR);
        mCrashEvent.setSystemVersion(Build.VERSION.SDK_INT);
        mCrashEvent.setDeviceModel(Build.MODEL);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        mCrashEvent.setScreenSize(new int[]{dm.widthPixels, dm.heightPixels});
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mCrashEvent.setNetEnv(cm.getActiveNetworkInfo().getTypeName());// FIXME: 2016/6/6 忘记申请权限（如果UncaughtExceptionHandler出了异常会ANR）
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
    public CrashEvent takeEvent() {
        return mCrashEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable =  new CrashTable(mContext);
        }
        return mTable;
    }

}
