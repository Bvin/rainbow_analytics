package cn.rainbow.sdk.analytics.track;

import android.content.Context;

/**
 * Created by 32967 on 2016/5/27.
 */
public class TrackerImpl implements Tracker{

    private EventTracker mEventTracker;
    private PageTracker mPageTracker;
    private String mPageName;
    private Context mContext;

    @Override
    public void attachContext(Context context) {
        mContext = context;
    }

    @Override
    public void initApp(int appId, int reportPolicy, long member_id) {

    }

    @Override
    public void beginLogPage(Context context) {
        if (mPageTracker == null) {
            mPageTracker = new PageTracker(context);
        }else {
            //上次endLogPage还没统计完？
        }
        mPageTracker.onPageStart(mPageName);
    }

    @Override
    public void endLogPage(Context context) {
        mPageName = context.getClass().getSimpleName();
        if (mPageTracker == null) {
            throw new RuntimeException("page track must call when begin");
        }
        mPageTracker.onEventEnd();
        mPageTracker = null;
    }

    @Override
    public void beginLogEvent(int eventId, String desc) {
        if (mEventTracker == null) {
            mEventTracker = new DefaultEventTracker(eventId, desc);
            mEventTracker.attachContext(mContext);
        }else {
            //endLogEvent？
        }
        mEventTracker.onEventStart();
    }

    @Override
    public void logEvent(int eventId, String desc) {
        if (mEventTracker == null) {
            //提前结束了
        }else {
            mEventTracker.onEvent();
        }
    }

    @Override
    public void endLogEvent(int eventId, String desc) {
        if (mEventTracker == null) {
            //提前结束了
        }else {
            mEventTracker.onEventEnd();
        }
    }

    @Override
    public void logCrashInfo(int appId, String platform, String crash_log) {

    }
}
