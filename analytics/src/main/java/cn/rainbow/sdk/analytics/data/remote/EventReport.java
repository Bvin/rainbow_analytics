package cn.rainbow.sdk.analytics.data.remote;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2016/6/16.
 * <p>事件上报接口.
 */
public interface EventReport<T extends Event>{

    void report(T event, Callback callback);
}
