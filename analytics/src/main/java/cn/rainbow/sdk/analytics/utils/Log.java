package cn.rainbow.sdk.analytics.utils;

import android.text.TextUtils;

import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class Log {

    private static final boolean sEnableLog = THAnalytics.getCurrentConfig().isEnableDebugLog();

    public static void i(String tag, String msg) {
        if (sEnableLog)
            android.util.Log.i(tag, checkEmpty(msg));
    }

    public static void d(String tag, String msg) {
        if (sEnableLog)
            android.util.Log.d(tag, checkEmpty(msg));
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sEnableLog)
            android.util.Log.e(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (sEnableLog)
            android.util.Log.e(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sEnableLog)
            android.util.Log.w(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        if (sEnableLog)
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, Throwable tr) {
        if (sEnableLog)
            android.util.Log.w(tag, tr);
    }

    private static String checkEmpty(String content) {
        if (TextUtils.isEmpty(content)) {
            return "please check string that are empty or null!";
        } else return content;
    }
}
