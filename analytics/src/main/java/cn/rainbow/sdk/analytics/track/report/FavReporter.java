package cn.rainbow.sdk.analytics.track.report;

import cn.rainbow.sdk.analytics.data.remote.AbsEventReporter;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;

/**
 * Created by bvin on 2016/6/16.
 */
public class FavReporter extends AbsEventReporter<FavoriteEvent>{

    public FavReporter(FavoriteEvent event) {
        super(event);
    }

    public class Keys{
        public static final String CHANNEL_ID = "c";
        public static final String MERCHANT_ID = "mid";
        public static final String GOODS_ID = "gid";
        public static final String GOODS_SKU_CODE = "gsku";
        public static final String GOODS_NAME = "gn";
        public static final String GOODS_IMAGE = "gi";
        public static final String DEVICE_ID = "id";
        public static final String USER_ID = "uid";
        public static final String OPERATION = "op";
    }
}
