package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.CrashHandler;
import cn.rainbow.sdk.analytics.track.AppTracker;
import cn.rainbow.sdk.analytics.track.CrashTracker;
import cn.rainbow.sdk.analytics.track.DefaultEventTracker;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.PageTracker;

/**
 * Created by 32967 on 2016/5/27.
 */
public class TrackerImpl implements Tracker{

    private static final String TAG = "TrackerImpl";

    private AbsEventTracker mEventTracker;
    private AppTracker mAppTracker;
    private PageTracker mPageTracker;
    private CrashTracker mCrashTracker;
    private String mPageName;
    private Context mContext;
    private Config mConfig = new Config();//empty setConfig

    @Override
    public void attachContext(Context context) {
        mContext = context;
    }

    @Override
    public void config(Config config) {
        mConfig = config;
    }

    @Override
    public Config getCurrentConfig() {
        return mConfig;
    }

    @Override
    public void initApp(Context context,int appId, int reportPolicy, long member_id) {
        if (mAppTracker == null) {
            mAppTracker = new AppTracker(context, appId);
        }
        mAppTracker.onStart();
    }

    @Override
    public void onAppExit() {
        mAppTracker.onExit();
    }

    @Override
    public void beginLogPage(Context context) {
        if (mPageTracker == null) {
            mPageTracker = new PageTracker(context);
        }else {
            //上次endLogPage还没统计完？
        }
        if (TextUtils.isEmpty(mPageName)) {
            mPageTracker.onPageStart();//root page
        } else {
            mPageTracker.onPageStartAfter(mPageName);
        }
        printDebugLog(TAG, "beginLogPage: 上一页->" + mPageName);
    }

    private void printDebugLog(String tag, String content) {
        if (mConfig != null) {
            if (mConfig.isEnableDebugLog()) {
                Log.d(tag, content);
            }
        }
    }

    @Override
    public void endLogPage(Context context) {
        mPageName = context.getClass().getName();
        if (mPageTracker == null) {
            throw new RuntimeException("page track must call when begin");
        }
        mPageTracker.onPageEnd();
        printDebugLog(TAG,"endLogPage:当前页—> "+mPageName);
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
    public void logCrashInfo(Context context, String log) {

        if (mCrashTracker == null) {
            mCrashTracker = new CrashTracker(context);
        }
        cn.rainbow.sdk.analytics.utils.Log.d(TAG+"#logCrashInfo",log);
        mCrashTracker.onCrash(log);
    }
}
