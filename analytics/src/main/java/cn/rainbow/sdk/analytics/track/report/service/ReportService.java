package cn.rainbow.sdk.analytics.track.report.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import cn.rainbow.sdk.analytics.track.report.LocalReporter;

/**
 * Created by bvin on 2016/8/12.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ReportService extends JobService {

    private static final int MESSAGE_WHAT = 1;
    private Handler mJobHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JobParameters parameters = (JobParameters) msg.obj;
            int jobId = parameters.getJobId();
            new LocalReporter(getApplicationContext()).report();
            jobFinished(parameters, false);
        }
    };

    @Override
    public boolean onStartJob(JobParameters params) {
        mJobHandler.sendMessage(Message.obtain(mJobHandler, MESSAGE_WHAT, params));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.removeMessages(MESSAGE_WHAT);
        return false;
    }
}
