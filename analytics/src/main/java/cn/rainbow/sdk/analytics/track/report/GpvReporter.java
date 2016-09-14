package cn.rainbow.sdk.analytics.track.report;

import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class GpvReporter extends AbsEventReporter<GoodsViewEvent>{

    public GpvReporter(GoodsViewEvent event) {
        super(event);
    }

    public static class Keys {
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String GOODS_ID = "gid";
        public static final String GOODS_NAME = "gn";
        public static final String GOODS_IMAGE = "gi";
        public static final String GOODS_CATEGORY1 = "gc1";
        public static final String GOODS_CATEGORY2 = "gc2";
        public static final String GOODS_CATEGORY3 = "gc3";
        public static final String ENTER_TIME = "et";
        public static final String LEAVE_TIME = "lt";
        public static final String DEVICE_ID = "id";
        public static final String USER_ID = "uid";
    }
}
