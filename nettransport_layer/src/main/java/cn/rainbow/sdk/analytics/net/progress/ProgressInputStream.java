package cn.rainbow.sdk.analytics.net.progress;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

/**
 * Created by bvin on 2016/8/19.
 */
public class ProgressInputStream extends FilterInputStream{

    private long bytesRead = 0;
    private ProgressListener mProgressListener;

    /**
     * Constructs a new {@code FilterInputStream} with the specified input
     * stream as source.
     * <p/>
     * <p><strong>Warning:</strong> passing a null source creates an invalid
     * {@code FilterInputStream}, that fails on every method that is not
     * overridden. Subclasses should check for null in their constructors.
     *
     * @param in the input stream to filter reads on.
     */
    protected ProgressInputStream(InputStream in) {
        this(in, null);
    }

    public ProgressInputStream(InputStream in, ProgressListener progressListener) {
        super(in);
        mProgressListener = progressListener;
    }

    @Override
    public int read() throws IOException {
        int b = in.read();
        if (b != -1){
            bytesRead += 1;
            reportTransferProgress();
        }
        return b;
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        try {
            int count = in.read(buffer, byteOffset, byteCount);
            if (count != -1){
                bytesRead += count;
                reportTransferProgress();
            }
            return count;
        }catch (InterruptedIOException e){
            bytesRead += e.bytesTransferred;
            reportTransferProgress();
            throw  e;
        }
    }

    private void reportTransferProgress() {
        if (mProgressListener != null) {
            mProgressListener.onRequestProgress(bytesRead);
        }
    }

}
