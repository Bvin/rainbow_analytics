package cn.rainbow.sdk.analytics.data.remote.httplite;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import alexclin.httplite.listener.RequestListener;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.utils.Log;


/**
 * Created by bvin on 2016/6/14.
 */

public class TestApi {

    public void test(){
        HttpLiteBuilder mBuilder = URLite.create();
        HttpLite httpLite = mBuilder.addResponseParser(new GsonParser()).build();
        Api api = httpLite.retrofit(Api.class, new RequestListener() {
            @Override
            public void onRequest(HttpLite lite, Request request, Type resultType) {
                Log.d("onRequest",request.toString());
            }
        });
        api.reportFav("channel", "merchant", "gid", "gsku", "gn", "gi", "id", "uid", 2001, new Callback<Model>() {
            @Override
            public void onSuccess(Request req, Map<String, List<String>> headers, Model result) {
                Log.d("reportFav:"+result.toString(),req.toString());
            }

            @Override
            public void onFailed(Request req, Exception e) {
                Log.d("reportFav",e.getMessage());
            }
        });
    }
}
