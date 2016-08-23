package cn.rainbow.sdk.analytics.track.buz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import alexclin.httplite.annotation.Param;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.remote.ApiConfig;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.net.RequestService;
import cn.rainbow.sdk.analytics.track.AbsEventTracker;
import cn.rainbow.sdk.analytics.track.report.CartReporter;
import cn.rainbow.sdk.analytics.track.report.OrderReporter;

/**
 * Created by bvin on 2016/6/14.
 */
public class CartTracker extends AbsEventTracker<CartEvent> {

    public static final int EVENT_ID = 1040;

    private CartEvent mEvent;
    private CartTable mTable;

    public CartTracker(Context context) {
        super(context,EVENT_ID, "购物车统计");
    }

    public void startTrack(CartEvent event){
        if (event == null) {
            throw new RuntimeException("event must not be null.");
        }
        mEvent = event;
        onEventStart();
        mEvent.setChannelId(THAnalytics.getCurrentConfig().getChannelId());
        onEventEnd();
    }

    @Override
    protected void push() {
        ContentValues cv = mEvent.saveValues();
        if (cv != null) {
            cv.put("type", "cart");
        }
        StringBuilder sb = new StringBuilder(ApiConfig.HOST+ApiConfig.URL_ORDER);
        sb.append("?");
        for (Map.Entry<String, Object> entry : cv.valueSet()) {
            if (entry.getKey().equals(OrderTable.Columns.GOODS_LIST)) continue;//不用传i，已通过数组分开传了
            if (entry.getKey().equals(OrderTable.Columns.ORDER_NUMBER)) {
                sb.append(OrderReporter.Keys.ORDER_NUMBER);//由于on是数据库表的保留字段需要转换一下
            } else {
                sb.append(entry.getKey());
            }
            sb.append("=").append(entry.getValue()).append("&");
        }
        if (sb.toString().endsWith("&")) {
            sb.delete(sb.toString().length() - 1, sb.toString().length());
        }
        RequestService.start(mContext, sb.toString(), null, -1);
        //new CartReporter(mEvent).push(this);
    }

    @Override
    public CartEvent takeEvent() {
        return mEvent;
    }

    @Override
    public SQLTable takeTable() {
        if (mTable == null) {
            mTable = new CartTable(mContext);
        }
        return mTable;
    }
}
