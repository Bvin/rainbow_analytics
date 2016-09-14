package cn.rainbow.sdk.thanalytics;

import android.app.Application;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config();
        config.enableDebugLog(true);//打开log
        config.enableCrashTrack(true);//开启崩溃收集
        config.setTestEnv(true);
        config.setPushStrategy(Config.PUSH_STRATEGY_BATCH_BOOTSTRAP);
        config.setPushRemote(true);
        config.setDelayMsWhenPushLocal(1000);
        //config.setUseJobScheduler(true);
        THAnalytics.onAppStart(this, config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        THAnalytics.onAppExit();
    }
}
