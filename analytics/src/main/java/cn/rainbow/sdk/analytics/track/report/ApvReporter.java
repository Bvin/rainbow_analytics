package cn.rainbow.sdk.analytics.track.report;

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
        api.reportAPV("apv",event.getChannelId(),
                event.getMerchantId(),
                pageName,
                event.getAppVersion(),
                event.getStartDate(),
                event.getEndDate(),
                event.getDevice(),
                event.getSystem(),
                event.getSystemVersion(),
                event.getDeviceId(),
                event.getTraceNumber(),
                new BaseResponseCallback(callback));
    }

    public class Keys {
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String PAGE = "u";
        public static final String APP_VERSION = "v";
        public static final String ENTER_TIME = "et";
        public static final String LEAVE_TIME = "lt";
        public static final String MOBILE = "mb";
        public static final String OS = "o";
        public static final String OS_VERSION = "ov";
        public static final String DEVICE_ID = "id";
        public static final String TRACE_NUMBER = "tn";
    }
}
