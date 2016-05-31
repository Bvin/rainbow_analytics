package cn.rainbow.sdk.analytics.track;

/**
 * Created by 32967 on 2016/5/27.
 */
public interface Tracker {

    /**
     * 初始化.
     * @param appId app编号
     * @param reportPolicy 发送策略
     * @param member_id 会员编号
     */
    void initApp(int appId, int reportPolicy, long member_id);

    /**
     * @brief 开始页面统计。
     * @param page  页面名称。
     */
    void beginLogPage(String page);

    /**
     * @brief 结束页面统计。
     * @param page  页面名称。
     */
    void endLogPage(String page);

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
