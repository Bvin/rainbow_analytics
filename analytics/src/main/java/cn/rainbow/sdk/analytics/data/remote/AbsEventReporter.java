package cn.rainbow.sdk.analytics.data.remote;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alexclin.httplite.HttpLite;
import alexclin.httplite.HttpLiteBuilder;
import alexclin.httplite.listener.Callback;
import alexclin.httplite.url.URLite;
import cn.rainbow.sdk.analytics.data.remote.httplite.GsonParser;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/16.
 * <p>事件上报抽象基类.
 */
public abstract class AbsEventReporter<T extends Event> implements EventReport<T>{

    private T mEvent;
    protected HttpLite mHttpLite;

    public AbsEventReporter(T event) {
        mEvent = event;
        HttpLiteBuilder mBuilder = URLite.create();
        mHttpLite = mBuilder.addResponseParser(new GsonParser()).build();
        mHttpLite.setBaseUrl(ApiConfig.HOST);
    }

    /**
     * 推送.
     * @param callback 回掉
     */
    public void push(Callback callback){
        report(mEvent,callback);
    }

    /**
     * URL编码.
     * @param content 内容
     * @return 成功返回URL编码后的内容，否则返元原来内容
     */
    protected String urlEncode(String content){
        if (TextUtils.isEmpty(content)) return content;
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
}
