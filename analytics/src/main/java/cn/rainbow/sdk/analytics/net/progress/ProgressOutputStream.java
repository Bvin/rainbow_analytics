package cn.rainbow.sdk.analytics.net.progress;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by bvin on 2016/8/18.
 */
public class ProgressOutputStream extends OutputStream {

    private OutputStream mOutputStream;
    private ProgressListener mProgressListener;
    private long mProgress;

    public ProgressOutputStream(OutputStream outputStream, ProgressListener progressListener) {
        mOutputStream = outputStream;
        mProgressListener = progressListener;
    }

    @Override
    public void write(int oneByte) throws IOException {
        mOutputStream.write(oneByte);
        mProgress++;
        if (mProgressListener != null) {
            mProgressListener.onRequestProgress(mProgress);
        }
    }
}
