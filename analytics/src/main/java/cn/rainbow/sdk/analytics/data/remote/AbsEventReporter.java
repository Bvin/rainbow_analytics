package cn.rainbow.sdk.analytics.data.remote;

import com.litesuits.http.LiteHttp;
import com.litesuits.http.concurrent.OverloadPolicy;
import com.litesuits.http.concurrent.SchedulePolicy;
import com.litesuits.http.listener.HttpListener;

import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.track.report.EventRequest;

/**
 * Created by bvin on 2016/6/16.
 * <p>事件上报抽象基类.
 */
public abstract class AbsEventReporter<T extends Event> implements EventReport<T>{

    private T mEvent;
    protected LiteHttp mHttpLite;

    public AbsEventReporter(T event) {
        mEvent = event;
        mHttpLite = LiteHttp.build(null)
                .setConcurrentSize(1)//核心并发数1
                .setWaitingQueueSize(2)//等待队列数量2
                .setSchedulePolicy(SchedulePolicy.FirstInFistRun)//先进先执行
                .setOverloadPolicy(OverloadPolicy.DiscardCurrentTask)//满载抛弃
                .setBaseUrl(ApiConfig.HOST+ApiConfig.URL_REPORT)
                //.setJsonConvertor()//默认Gson
                .setDefaultMaxRetryTimes(0)
                .create();
        //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/

    }

    /**
     * 推送.
     * @param callback 回掉
     */
    public void push(HttpListener<Model> callback){
        report(mEvent,callback);
    }

    @Override
    public void report(T event, HttpListener<Model> callback) {
        EventRequest request = new EventRequest(event);
        request.setHttpListener(callback);
        mHttpLite.executeAsync(request);
    }

}
