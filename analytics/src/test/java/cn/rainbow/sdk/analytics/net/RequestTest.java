package cn.rainbow.sdk.analytics.net;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.reader.ResponseReader;
import cn.rainbow.sdk.analytics.net.response.reader.StringResponseReader;

/**
 * Created by bvin on 2016/8/19.
 */
public class RequestTest {

    @Test
    public void testPerform() throws Exception {
        Request<String> request = new Request<>();
        String url = "http://192.168.148.162:8010/report?rt=event";
        String method = "GET";
        ResponseReader<String> responseReader = new StringResponseReader();
        Map<String,String> map = new HashMap<>();
        url = url+"?wd=一";
        Response<String> response = request.perform(url, method, map, null, null, null, responseReader);
        Assert.assertNotNull(response.get());
        //Assert.assertFalse(TextUtils.isEmpty(response.get()));
        System.out.println(response.getNetworkResponse().getContentLength());
        Assert.assertNotEquals(response.getNetworkResponse().getContentLength(),-1);
        System.out.println(response.get());
    }
}