package cn.rainbow.sdk.analytics.data.remote.httplite;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import alexclin.httplite.impl.StringParser;

/*
 * Created by bvin on 2016/6/14.
 * */


public class GsonParser extends StringParser{

    private Gson gson;

    public GsonParser() {
        gson = new Gson();
    }

    @Override
    public <T> T parseResponse(String content, Type type) throws Exception {
        return gson.fromJson(content,type);
    }

    @Override
    public boolean isSupported(Type type) {
        return true;
    }
}
