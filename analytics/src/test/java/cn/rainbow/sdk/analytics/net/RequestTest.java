package cn.rainbow.sdk.analytics.net;

import org.junit.Assert;
import org.junit.Test;

import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.ResponseReader;
import cn.rainbow.sdk.analytics.net.response.StringResponseReader;

/**
 * Created by bvin on 2016/8/19.
 */
public class RequestTest {

    @Test
    public void testPerform() throws Exception {
        Request<String> request = new Request<>();
        String url = "https://www.baidu.com/";
        String method = "GET";
        ResponseReader<String> responseReader = new StringResponseReader();
        Response<String> response = request.perform(url, method, null, null, null, null, responseReader);
        if (response != null) {
            Assert.assertNotNull(response.get());
            System.out.println(response.get());
        }
    }
}