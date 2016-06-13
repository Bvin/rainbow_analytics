package cn.rainbow.sdk.analytics;

import android.content.Context;

import cn.rainbow.sdk.analytics.event.marketing.GoodsViewEvent;
import cn.rainbow.sdk.analytics.proxy.Tracker;
import cn.rainbow.sdk.analytics.proxy.TrackerImpl;

/**
 * Created by bvin on 2016/5/27.
 */
public class THAnalytics {

    private static Tracker mTracker;

    static {
        mTracker = new TrackerImpl();
    }

    public static void setConfig(Config config){
        mTracker.config(config);
    }

    public static Config getCurrentConfig(){
        return mTracker.getCurrentConfig();
    }

    //如果开启Crash跟踪，调用此方法将会开启...
    public static void onAppStart(Context context) {
        mTracker.initApp(context, 1208, 0, 0);
        if (mTracker.getCurrentConfig().isEnableCrashTrack()) {
            CrashHandler crashHandler = new CrashHandler(context);
            Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        }
    }

    public static void onAppExit() {
        mTracker.onAppExit();
    }

    public static void onResume(Context context) {
        mTracker.beginLogPage(context);
    }

    public static void onPause(Context context) {
        mTracker.endLogPage(context);
    }

    public static void reportCrash(Context context, String log) {
        mTracker.logCrashInfo(context, log);
    }

    /**
     *
     * @param context 上下文
     * @param receiverData 接收数据（商品信息和用户信息等）
     */
    public static void startGoodsPage(Context context, GoodsViewEvent receiverData){
        mTracker.startGoodsPage(context,receiverData);
    }

    public static void stopGoodsPage(){
        mTracker.stopGoodsPage();
    }
}
