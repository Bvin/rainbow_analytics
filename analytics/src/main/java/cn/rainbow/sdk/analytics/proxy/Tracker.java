package cn.rainbow.sdk.analytics.proxy;

import android.content.Context;

import cn.rainbow.sdk.analytics.Config;

/**
 * Created by 32967 on 2016/5/27.
 */
public interface Tracker {

    void attachContext(Context context);

    void config(Config config);

    Config getCurrentConfig();

    /**
     * 初始化.
     * @param appId app编号
     * @param reportPolicy 发送策略
     * @param member_id 会员编号
     */
    void initApp(int appId, int reportPolicy, long member_id);

    /**
     * @brief 开始页面统计。
     * @param context  页面名称。
     */
    void beginLogPage(Context context);

    /**
     * @brief 结束页面统计。
     * @param context  页面名称。
     */
    void endLogPage(Context context);

    /**
     * @brief 统计事件。
     * @param eventId  事件编号。
     * @param desc  事件描述。
     */
    void logEvent(int eventId, String desc);

    /**
     * @brief 开始统计事件。
     * @param eventId  事件编号。
     * @param desc  事件描述。
     */
    void beginLogEvent(int eventId, String desc);

    /**
     * @brief 结束统计事件。
     * @param eventId  事件编号。
     * @param desc  事件描述。
     */
    void endLogEvent(int eventId, String desc);

    /**
     * @brief 应用奔溃日志统计。
     * @param appId app编号。
     * @param platform  平台（iPhone, iPad, Android Phone, Android Pad）
     * @param crash_log  奔溃内容详情。
     */
    void logCrashInfo(int appId, String platform, String crash_log);
}
