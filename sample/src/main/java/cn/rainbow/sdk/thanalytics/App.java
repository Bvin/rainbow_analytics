package cn.rainbow.sdk.thanalytics;

import android.app.Application;
import android.os.Handler;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.core.Config;

/**
 * Created by bvin on 2016/6/1.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config();
        config.setEnable(true);
        config.setTestEnv(true);
        config.setRealTime(true);
        config.setTaskInterval(2500);
        //config.setHost();
        THAnalytics.init(this, config);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                THAnalytics.reportLocal();
            }
        },1500);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
