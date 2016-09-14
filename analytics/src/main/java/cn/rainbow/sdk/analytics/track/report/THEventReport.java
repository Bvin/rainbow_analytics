package cn.rainbow.sdk.analytics.track.report;

import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.event.buz.THEvent;

/**
 * Created by bvin on 2016/8/10.
 */
public class THEventReport extends AbsEventReporter<THEvent>{
    public THEventReport(THEvent event) {
        super(event);
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
