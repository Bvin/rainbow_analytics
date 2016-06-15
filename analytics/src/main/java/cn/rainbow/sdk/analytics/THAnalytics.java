package cn.rainbow.sdk.analytics;

import android.content.Context;

import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.proxy.Tracker;
import cn.rainbow.sdk.analytics.proxy.TrackerImpl;

/**
 * Created by bvin on 2016/5/27.
 */
public class THAnalytics {

    private static Tracker mTracker;

    //new 本身是为了jar包在别的项目调用静态方法会出现ExceptionInInitializerError
    private static THAnalytics sTHAnalytics = new THAnalytics();

    private THAnalytics() {
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
     * 开始统计商品页面.
     * <P>在Activity或Fragment的onResume()调用（需要准备数据）.
     * @param context 上下文
     * @param receiverData 接收数据（商品信息和用户信息等）
     */
    public static void startGoodsPage(Context context, GoodsViewEvent receiverData){
        mTracker.startGoodsPage(context,receiverData);
    }

    /**
     * 结束统计商品页面.
     * <P>在商品详情页面的onPause()方法调用.
     */
    public static void stopGoodsPage(){
        mTracker.stopGoodsPage();
    }

    /**
     * 统计购物车（在添加/删除商品/提交购物车时调用）.
     * @param context 上下文
     * @param event 购物车事件
     */
    public static void trackCart(Context context, CartEvent event){
        mTracker.trackCart(context,event);
    }

    /**
     * 统计商品收藏（在收藏/取消收藏商品时调用）.
     * @param context 上下文
     * @param event 收藏事件
     */
    public static void trackFavorite(Context context, FavoriteEvent event){
        mTracker.trackFav(context,event);
    }

    /**
     * 统计订单（在提交/支付/支付完成/取消/退货/退款时调用）.
     * @param context 上下文
     * @param event 订单事件
     */
    public static void trackOrder(Context context, OrderEvent event){
        mTracker.trackOrder(context,event);
    }
}
