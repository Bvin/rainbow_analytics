package cn.rainbow.sdk.analytics.proxy;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.THAnalytics;
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
import cn.rainbow.sdk.analytics.track.report.LocalReporter;
import cn.rainbow.sdk.analytics.track.report.service.ReportService;

/**
 * Created by 32967 on 2016/5/27.
 */
public class TrackerImpl implements Tracker{

    private AbsEventTracker mEventTracker;
    private AppTracker mAppTracker;
    private PageTracker mPageTracker;
    private THPageTracker mMarketingPageTracker;
    private GoodsPagerTracker mGoodsPagerTracker;
    private CrashTracker mCrashTracker;
    private String mPageName;
    private String mPreviousPageName;
    private Config mConfig = new Config();//empty setConfig

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
        long delayMs = getCurrentConfig().getDelayMsWhenPushLocal();
        //PUSH_STRATEGY_BATCH_BOOTSTRAP需不需要判断是否PushRemoteEnable?
        //PushRemoteEnable只在实时传有效，还是所有策略都全控制
        if (/*getCurrentConfig().isPushRemoteEnable() && */getCurrentConfig().getPushStrategy() == Config.PUSH_STRATEGY_BATCH_BOOTSTRAP) {
            if (getCurrentConfig().isUseJobScheduler() && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                //5.0以上闲时上传
                JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                ComponentName componentName = new ComponentName(context, ReportService.class.getName());
                JobInfo.Builder jobBuilder = new JobInfo.Builder(1, componentName);
                //jobBuilder.setRequiresDeviceIdle(true);//设备空闲的时候
                if (getCurrentConfig().isPushOnlyWifi()) {
                    jobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);//非移动网络
                }
                //jobBuilder.setPeriodic(3000);
                jobBuilder.setMinimumLatency(delayMs);
                Log.d(THAnalytics.TAG, "report local event use JobScheduler" );
                int resultCode = jobScheduler.schedule(jobBuilder.build());
                if (resultCode < 0) {
                    Log.d(THAnalytics.TAG, "report local event fail schedule code :" + resultCode);
                }
            } else {
                //延时7s上传
                Log.d(THAnalytics.TAG, "report local event use Handler" );
                uploadLog(context, delayMs);
            }
        }
    }

    private void uploadLog(final Context context, long delayMs) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LocalReporter(context).report();
            }
        }, delayMs);

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
        printDebugLog(THAnalytics.TAG, "beginLogPage:当前页->"+context.getClass().getName()+" |上一页->" + mPageName);
        if (mPreviousPageName != null) {
            if (context.getClass().getName().equals(mPreviousPageName)) {
                printDebugLog(THAnalytics.TAG, "返回");
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
        printDebugLog(THAnalytics.TAG,"endLogPage:当前页—> "+mPageName);
        endPageTrack(mPageTracker);
        mPageTracker = null;

        //update trace number
        if (traceNumber != null) {
            THPageEvent event = (THPageEvent) mMarketingPageTracker.takeEvent();
            if (event != null) {
                event.setTraceNumber(traceNumber);
                mMarketingPageTracker.update(event);
            }
        }
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
        cn.rainbow.sdk.analytics.utils.Log.d(THAnalytics.TAG+"#logCrashInfo",log);
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
