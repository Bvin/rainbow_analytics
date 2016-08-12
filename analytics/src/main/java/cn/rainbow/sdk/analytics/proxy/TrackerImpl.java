package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.FavTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THPageTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.event.buz.THEvent;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;
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
import cn.rainbow.sdk.analytics.track.buz.THTracker;
import cn.rainbow.sdk.analytics.track.report.ApvReporter;
import cn.rainbow.sdk.analytics.track.report.CartReporter;
import cn.rainbow.sdk.analytics.track.report.FavReporter;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;
import cn.rainbow.sdk.analytics.track.report.OrderReporter;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

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
    private String mPreviousPageName;
    private Config mConfig = new Config();//empty setConfig

    private boolean mUploadTraceNumber = true;

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
        //PUSH_STRATEGY_BATCH_BOOTSTRAP需不需要判断是否PushRemoteEnable?
        //PushRemoteEnable只在实时传有效，还是所有策略都全控制
        if (/*getCurrentConfig().isPushRemoteEnable() && */getCurrentConfig().getPushStrategy() == Config.PUSH_STRATEGY_BATCH_BOOTSTRAP) {
            uploadLog(context, 3000);//上传以前的统计日志
        }
    }

    private void uploadLog(final Context context, long delayMs) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                uploadApv(context);
                uploadGpv(context);
                uploadCartEvents(context);
                uploadFavEvents(context);
                uploadOrderEvents(context);
                uploadTHEvents(context);
            }
        }, delayMs);

    }

    private void uploadApv(Context context) {
        THPageTable table = new THPageTable(context);
        List<THPageEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (THPageEvent event : list) {
            new ApvReporter(event).push(callback(event, table));
        }
    }

    private void uploadGpv(Context context) {
        GoodsTable table = new GoodsTable(context);
        List<GoodsViewEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (GoodsViewEvent event : list) {
            new GpvReporter(event).push(callback(event, table));
        }
    }

    private void uploadCartEvents(Context context) {
        CartTable table = new CartTable(context);
        List<CartEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (CartEvent event : list) {
            new CartReporter(event).push(callback(event, table));
        }
    }

    private void uploadFavEvents(Context context) {
        FavTable table = new FavTable(context);
        List<FavoriteEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (FavoriteEvent event : list) {
            new FavReporter(event).push(callback(event, table));
        }
    }

    private void uploadOrderEvents(Context context) {
        OrderTable table = new OrderTable(context);
        List<OrderEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (OrderEvent event : list) {
            new OrderReporter(event).push(callback(event, table));
        }
    }

    private void uploadTHEvents(Context context) {
        THEventTable table = new THEventTable(context);
        List<THEvent> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (THEvent event : list) {
            new THEventReport(event).push(callback(event, table));
        }
    }

    private Callback<Model> callback(final Event event, final AbsEventTable table) {
        return new Callback<Model>() {
            @Override
            public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
                if (model != null && model.getRet() == 200) {
                    table.delete(event);
                }
            }

            @Override
            public void onFailed(Request request, Exception e) {

            }
        };
    }

    @Override
    public void onAppExit() {
        mAppTracker.onExit();
    }

    @Override
    public void beginLogPage(Context context) {
        if (mPageTracker == null) {
            mPageTracker = new PageTracker(context);
            mPageTracker.setEnable(false);//关闭常规页面统计
        }
        if (mMarketingPageTracker == null) {
            mMarketingPageTracker = new THPageTracker(context);
        }
        printDebugLog(TAG, "beginLogPage:当前页->"+context.getClass().getName()+" |上一页->" + mPageName);
        if (mPreviousPageName!=null) {
            if (context.getClass().getName().equals(mPreviousPageName)) {
                printDebugLog(TAG, "返回");
                mUploadTraceNumber = false;
            }
        }
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

    public void updatePageEvent(THPageEvent event){
        mMarketingPageTracker.update(event);
    }

    @Override
    public void endLogPage(Context context, String traceNumber) {
        mPreviousPageName = mPageName;
        mPageName = context.getClass().getName();
        printDebugLog(TAG,"endLogPage:当前页—> "+mPageName);
        endPageTrack(mPageTracker);
        mPageTracker = null;

        //update trace number
        if(mUploadTraceNumber) {
            if (traceNumber != null) {
                THPageEvent event = (THPageEvent) mMarketingPageTracker.takeEvent();
                event.setTraceNumber(traceNumber);
                mMarketingPageTracker.update(event);
            }
        }
        mUploadTraceNumber = true;
        endPageTrack(mMarketingPageTracker);
        mMarketingPageTracker = null;
    }

    private void endPageTrack(PageTracker pageTracker) throws IllegalStateException{
        if (pageTracker == null) {
            throw new IllegalStateException("page track must call when begin");
        }
        pageTracker.onPageEnd();
    }

    @Override
    public void beginLogEvent(Context context,int eventId, String desc) {
        if (mEventTracker == null) {
            mEventTracker = new DefaultEventTracker(context,eventId, desc);
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
    public void stopGoodsPage() throws IllegalStateException{
        endPageTrack(mGoodsPagerTracker);
        mGoodsPagerTracker = null;
    }

    @Override
    public void trackCart(Context context, CartEvent eventData) {
        new CartTracker(context).startTrack(eventData);
    }

    @Override
    public void trackFav(Context context, FavoriteEvent eventData) {
        new FavTracker(context).startTrack(eventData);
    }

    @Override
    public void trackOrder(Context context, OrderEvent eventData) {
        new OrderTracker(context).startTrack(eventData);
    }

    @Override
    public void trackEvent(Context context, THEvent eventData) {
        new THTracker(context).startTrack(eventData);
    }
}
