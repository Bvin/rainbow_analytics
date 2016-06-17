package cn.rainbow.sdk.analytics.track.report;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;

/**
 * APP页面浏览统计(APP-Page-View)上报.
 */
public class ApvReporter extends AbsEventReporter<THPageEvent> {

    public ApvReporter(THPageEvent event) {
        super(event);
    }

    @Override
    public void report(THPageEvent event, Callback callback) {
        Api api = mHttpLite.retrofit(Api.class, new PreRequestListener());
        String pageName = urlEncode(event.getUrl());
        api.reportAPV(event.getChannelId(), event.getMerchantId(), pageName, event.getAppVersion(), event.getStartDate(),
                event.getEndDate(), event.getDevice(), event.getSystem(), event.getSystemVersion(), event.getDeviceId(), new BaseResponseCallback(callback));
    }
}
