package cn.rainbow.sdk.analytics;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by bvin on 2016/6/6.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public CrashHandler(Context context) {
        mContext = context;
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {//此方法里面的代码流如果发生异常会出现ANR
        //throwable.printStackTrace();
        THAnalytics.reportCrash(mContext, getErrorInfo(throwable));
        mUncaughtExceptionHandler.uncaughtException(thread,throwable);
        //android.os.Process.killProcess(android.os.Process.myPid());
        //System.exit(1);
    }

    private String getErrorInfo(Throwable th) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        th.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }
}
