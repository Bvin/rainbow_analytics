package cn.rainbow.sdk.thanalytics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.rainbow.sdk.analytics.THAnalytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        THAnalytics.getInstance().newTracker().attachContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        THAnalytics.getInstance().newTracker().beginLogEvent(0,"TEST");
    }

    @Override
    protected void onPause() {
        super.onPause();
        THAnalytics.getInstance().newTracker().endLogEvent(0,"TEST");
    }
}
