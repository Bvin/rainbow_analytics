package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THEventTable;
import cn.rainbow.sdk.analytics.event.buz.THEvent;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.THEventReport;

/**
 * Created by bvin on 2016/8/10.
 */
public class THTracker extends AbsEventTracker<THEvent>{


    private THEvent mEvent;
    private THEventTable mTable;

    public THTracker(Context context) {
        super(context, 1356, "通用事件");
    }

    public void startTrack(THEvent event){
        if (event == null) {
            throw new RuntimeException("event must not be null.");
        }
        mEvent = event;
        onEventStart();
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();
    }

    @Override
    public THEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new THEventTable(mContext);
        }
        return mTable;
    }

    @Override
    protected void push() {
        new THEventReport(mEvent).push(listener());
    }
}
