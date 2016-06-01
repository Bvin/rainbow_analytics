package cn.rainbow.sdk.analytics;

import android.content.Context;

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

    public static void config(Config config){
        mTracker.config(config);
    }

    public static Config getCurrentConfig(){
        return mTracker.getCurrentConfig();
    }

    public static void onAppStart(Context context) {
        mTracker.initApp(context, 1208, 0, 0);
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
}
