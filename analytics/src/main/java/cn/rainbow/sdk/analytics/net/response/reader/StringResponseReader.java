package cn.rainbow.sdk.analytics.net.response.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.rainbow.sdk.analytics.net.progress.ProgressInputStream;
import cn.rainbow.sdk.analytics.net.progress.ProgressListener;

/**
 * Created by bvin on 2016/8/18.
 */
public class StringResponseReader implements ResponseReader<String>{

    private ProgressListener mProgressListener;

    public StringResponseReader(ProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    public StringResponseReader() {
    }

    @Override
    public String read(InputStream inputStream) throws IOException {
        ProgressInputStream pis = new ProgressInputStream(inputStream, mProgressListener);
        BufferedReader reader  = new BufferedReader(new InputStreamReader(pis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
