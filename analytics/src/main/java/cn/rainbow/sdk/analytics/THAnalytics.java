package cn.rainbow.sdk.analytics;

import cn.rainbow.sdk.analytics.track.Tracker;
import cn.rainbow.sdk.analytics.track.TrackerImpl;

/**
 * Created by 32967 on 2016/5/27.
 */
public class THAnalytics {

    private static THAnalytics ourInstance = new THAnalytics();

    public static THAnalytics getInstance() {
        return ourInstance;
    }

    private THAnalytics() {
    }

    public Tracker newTracker() {
        return new TrackerImpl();
    }
}
