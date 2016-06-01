package cn.rainbow.sdk.thanalytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config config = new Config();
        config.enableDebugLog(true);
        THAnalytics.config(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        THAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        THAnalytics.onPause(this);
    }
}
