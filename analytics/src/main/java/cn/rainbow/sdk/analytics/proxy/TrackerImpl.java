package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.track.AppTracker;
import cn.rainbow.sdk.analytics.track.CrashTracker;
import cn.rainbow.sdk.analytics.track.DefaultEventTracker;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.buz.CartTracker;
import cn.rainbow.sdk.analytics.track.buz.FavTracker;
import cn.rainbow.sdk.analytics.track.buz.GoodsPagerTracker;
import cn.rainbow.sdk.analytics.track.buz.THPageTracker;
import cn.rainbow.sdk.analytics.track.PageTracker;
import cn.rainbow.sdk.analytics.track.buz.OrderTracker;

/**
 * Created by 32967 on 2016/5/27.
 */
public class TrackerImpl implements Tracker{

    private static final String TAG = "TrackerImpl";

    private AbsEventTracker mEventTracker;
    private AppTracker mAppTracker;
    private PageTracker mPageTracker;
    private THPageTracker mMarketingPageTracker;
    private GoodsPagerTracker mGoodsPagerTracker;
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
        }
        if (mMarketingPageTracker == null) {
            mMarketingPageTracker = new THPageTracker(context);
        }
        printDebugLog(TAG, "beginLogPage: 上一页->" + mPageName);
        beginPageTrack(mPageTracker);
        beginPageTrack(mMarketingPageTracker);
    }

    private void beginPageTrack(PageTracker pageTracker){
        if (TextUtils.isEmpty(mPageName)) {
            pageTracker.onPageStart();//root page
        } else {
            pageTracker.onPageStartAfter(mPageName);
        }
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
        printDebugLog(TAG,"endLogPage:当前页—> "+mPageName);
        endPageTrack(mPageTracker);
        mPageTracker = null;
        endPageTrack(mMarketingPageTracker);
        mMarketingPageTracker = null;
    }

    private void endPageTrack(PageTracker pageTracker){
        if (pageTracker == null) {
            throw new RuntimeException("page track must call when begin");
        }
        pageTracker.onPageEnd();
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

    @Override
    public void startGoodsPage(Context context, GoodsViewEvent eventData) {
        if (mGoodsPagerTracker == null) {
            mGoodsPagerTracker = new GoodsPagerTracker(context);
        }
        mGoodsPagerTracker.startGoodsPage(eventData);
    }

    @Override
    public void stopGoodsPage() {
        endPageTrack(mGoodsPagerTracker);
    }

    @Override
    public void trackCart(Context context, CartEvent eventData) {
        new CartTracker().startTrack(context, eventData);
    }

    @Override
    public void trackFav(Context context, FavoriteEvent eventData) {
        new FavTracker().startTrack(context, eventData);
    }

    @Override
    public void trackOrder(Context context, OrderEvent eventData) {
        new OrderTracker().startTrack(context, eventData);
    }
}
