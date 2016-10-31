package cn.rainbow.sdk.analytics;

import android.content.Context;
import android.util.Log;

import cn.rainbow.sdk.analytics.core.Config;
import cn.rainbow.sdk.analytics.core.Tracker;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/9/14.
 */
public class THAnalytics {

    private static final String TAG = "THAnalytics";

    private static Tracker mTracker;

    public static void init(Context context, Config config){
        mTracker = new Tracker(context, config);
    }

    public static int getChannelId(){
        if (mTracker != null) {
            return mTracker.getChannelId();
        }else {
            //未初始化
            Log.e(TAG, "THAnalytics未初始化");
            return -1;
        }
    }

    public static void reportLocal(){
        if (mTracker != null) {
            mTracker.reportLocal();
        }else {
            //未初始化
            Log.e(TAG, "THAnalytics未初始化");
        }
    }

    public static void track(Event event){
        if (mTracker != null) {
            mTracker.track(event);
        }else {
            //未初始化
            Log.e(TAG, "THAnalytics未初始化");
        }
    }

    public static Config getConfig() {
        if (mTracker != null) {
            return mTracker.getConfig();
        }
        return null;
    }
}
