package cn.rainbow.sdk.thanalytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.rainbow.sdk.analytics.THAnalytics;

/**
 * Created by bvin on 2016/6/1.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        THAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当
        THAnalytics.onPause(this, traceNumber());
    }

    public String traceNumber(){
        return null;
    }
}
