package cn.rainbow.sdk.analytics.data.remote.http;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;

/**
 * Created by bvin on 2016/8/12.
 * 请求池.
 */
public class RequestPool {

    private Request mRequest;

    public void enque(Request request){
        mRequest = request;
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite mHttpLite = mBuilder.addResponseParser(new GsonParser()).build();
    }


}
