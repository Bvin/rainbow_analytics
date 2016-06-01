package cn.rainbow.sdk.analytics;

import android.content.Context;

import cn.rainbow.sdk.analytics.track.Tracker;
import cn.rainbow.sdk.analytics.track.TrackerImpl;

/**
 * Created by bvin on 2016/5/27.
 */
public class THAnalytics {

    private static Tracker mTracker;

    static {
        mTracker = new TrackerImpl();
    }

    public static void onResume(Context context) {
        mTracker.beginLogPage(context);
    }

    public static void onPause(Context context) {
        mTracker.endLogPage(context);
    }
}
