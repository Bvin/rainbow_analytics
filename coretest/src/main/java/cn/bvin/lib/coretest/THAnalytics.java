package cn.bvin.lib.coretest;

import cn.rainbow.sdk.analytics.core.TransportService;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

/**
 * Created by bvin on 2016/9/14.
 */
public class THAnalytics {

    private boolean mIsRealTimeModel;

    public void track(Event event){
        if (mIsRealTimeModel){
            TransportService.startFromCurrent(null,"",event.toPersistableString());
        }else {
            PersistenceService.getInstance(null).save(event);
        }
    }
}
