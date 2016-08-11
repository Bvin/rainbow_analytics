package cn.rainbow.sdk.analytics.track.report;

import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.data.remote.httplite.Api;
import cn.rainbow.sdk.analytics.data.remote.httplite.BaseResponseCallback;
import cn.rainbow.sdk.analytics.data.remote.httplite.PreRequestListener;
import cn.rainbow.sdk.analytics.event.buz.THEvent;

/**
 * Created by bvin on 2016/8/10.
 */
public class THEventReport extends AbsEventReporter<THEvent>{
    public THEventReport(THEvent event) {
        super(event);
    }

    @Override
    public void report(THEvent event, Callback callback) {
        Api api = mHttpLite.retrofit(Api.class, new PreRequestListener());
        String pageName = urlEncode(event.getUrl());
        String link = urlEncode(event.getLink());
        api.reportEvent("events",
                event.getChannelId(),
                event.getMerchantId(),
                event.getEventId()+"",
                pageName,
                link,
                event.getId(),
                event.getUid(),
                event.getTraceNumber(),
                event.getElementTraceNumber(),
                new BaseResponseCallback(callback));
    }
    public class Keys {
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String EVENT_ID = "e";
        public static final String PAGE = "u";
        public static final String LINK = "l";
        public static final String TRACE_NUMBER = "tn";
        public static final String DEVICE_ID = "id";
        public static final String USER_ID = "uid";
        public static final String ELEMENT_TRACE_NUMBER = "etn";
    }
}
