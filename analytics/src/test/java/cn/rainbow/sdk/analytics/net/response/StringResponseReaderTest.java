package cn.rainbow.sdk.analytics.net.response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import cn.rainbow.sdk.analytics.net.Request;
import cn.rainbow.sdk.analytics.net.progress.ProgressListener;
import cn.rainbow.sdk.analytics.net.response.reader.StringResponseReader;

/**
 * Created by bvin on 2016/8/22.
 */
public class StringResponseReaderTest {

    private InputStream mInputStream;

    @Before
    public void setUp() throws Exception {
        Request<String> request = new Request<>();
        String url = "https://www.baidu.com/";
        String method = "GET";
        Response<String> response = request.perform(url, method, null, null, null, null, null);
        mInputStream = response.getNetworkResponse().getContent();
        System.out.println(response.get());
    }

    @Test
    public void testRead() throws Exception {
        System.out.println("start---");
        StringResponseReader reader = new StringResponseReader(new ProgressListener(){
            @Override
            public void onRequestProgress(long progress) {
                System.out.println(progress);
            }
        });
        Assert.assertNotNull(mInputStream);
        System.out.println(mInputStream.available());
        String result = reader.read(mInputStream);
        Assert.assertNotNull(result);
    }
}