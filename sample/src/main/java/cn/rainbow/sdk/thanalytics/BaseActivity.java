package cn.rainbow.sdk.thanalytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.event.ApvEvent;

/**
 * Created by bvin on 2016/6/1.
 */
public class BaseActivity extends AppCompatActivity{

    private ApvEvent mApvEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApvEvent = new ApvEvent();
        mApvEvent.setEnterTime(getCurrentDate());
    }

    @Override
    protected void onPause() {
        super.onPause();

        mApvEvent.setLeaveTime(getCurrentDate());
        mApvEvent.setTraceNumber(traceNumber());
        mApvEvent.setUrl(getClass().getName());
        THAnalytics.track(mApvEvent);
    }

    private SimpleDateFormat mDateFormat;

    protected String getCurrentDate(){
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
        return mDateFormat.format(new Date());
    }

    public String traceNumber(){
        return null;
    }
}
