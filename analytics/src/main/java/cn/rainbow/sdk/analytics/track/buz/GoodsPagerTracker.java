package cn.rainbow.sdk.analytics.track.buz;

import android.content.Context;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.event.PageEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.track.report.GpvReporter;

/**
 * Created by bvin on 2016/6/13.
 */
public class GoodsPagerTracker extends THPageTracker {

    private GoodsViewEvent mEvent;
    private GoodsTable mTable;

    public GoodsPagerTracker(Context context) {
        super(context);
    }

    @Override
    public void onPageStartAfter(String previousPage) {
        onEventStart();
    }

    /**
     * 开始统计商品页面.
     * @param eventData 不能为空
     */
    public void startGoodsPage(GoodsViewEvent eventData){
        if (eventData == null) {
            throw new RuntimeException("event must not be null.");
        }
        mEvent = eventData;
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onPageStartAfter(null);
    }

    @Override
    protected void push() {
        new GpvReporter(mEvent).push(listener());
    }


    @Override
    public PageEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new GoodsTable(mContext);
        }
        return mTable;
    }
}
