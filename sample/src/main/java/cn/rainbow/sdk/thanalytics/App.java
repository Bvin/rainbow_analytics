package cn.rainbow.sdk.thanalytics;

import android.app.Application;

import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        THAnalytics.onAppStart(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        THAnalytics.onAppExit();
    }
}