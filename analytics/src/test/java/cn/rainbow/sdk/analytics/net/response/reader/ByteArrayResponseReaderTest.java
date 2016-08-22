package cn.rainbow.sdk.analytics.net.response.reader;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import cn.rainbow.sdk.analytics.net.Request;
import cn.rainbow.sdk.analytics.net.response.Response;

import static org.junit.Assert.*;

/**
 * Created by bvin on 2016/8/22.
 */
public class ByteArrayResponseReaderTest {

    @Test
    public void testRead() throws Exception {
        Request<byte[]> request = new Request<>();
        String url = "https://www.baidu.com/";
        Response<byte[]> response = request.performGet(url, new ByteArrayResponseReader());
        Assert.assertNotNull(response.get());
        System.out.println(Arrays.toString(response.get()));
    }
}