package cn.rainbow.sdk.analytics.net.progress;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bvin on 2016/8/22.
 */
public class ProgressInputStreamTest {

    private long mContentLength;
    private InputStream mInputStream;
    private HttpURLConnection mURLConnection;

    @Before
    public void setUp() throws Exception {
        String url = "https://www.baidu.com/";
        String method = "GET";
        mURLConnection = (HttpURLConnection) new URL(url).openConnection();
        mURLConnection.setRequestMethod(method);
        mURLConnection.setDoInput(true);
        mInputStream = mURLConnection.getInputStream();
        mContentLength = mURLConnection.getContentLength();
        System.out.println(mContentLength);
    }

    @Test
    public void testRead() throws Exception {
        Assert.assertNotNull(mInputStream);
        ProgressInputStream pis = new ProgressInputStream(mInputStream, new ProgressListener() {
            @Override
            public void onRequestProgress(long progress) {
                System.out.println(progress*100/mContentLength);
            }
        });
        BufferedReader reader = new BufferedReader(new InputStreamReader(pis));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine())!=null){
            sb.append(s);
        }
        /*char[] buffer = new char[1024];
        while (reader.read(buffer)!=-1){

        }*/
        /*byte[] buffer = new byte[1024];
        while (pis.read(buffer)!=-1){}*/
    }

    @After
    public void tearDown() throws Exception {
        mURLConnection.disconnect();
    }
}