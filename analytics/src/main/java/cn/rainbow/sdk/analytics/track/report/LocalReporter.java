package cn.rainbow.sdk.analytics.track.report;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.local.db.AbsEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.FavTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THPageTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.event.buz.CartEvent;
import cn.rainbow.sdk.analytics.event.buz.FavoriteEvent;
import cn.rainbow.sdk.analytics.event.buz.GoodsViewEvent;
import cn.rainbow.sdk.analytics.event.buz.OrderEvent;
import cn.rainbow.sdk.analytics.event.buz.THEvent;
import cn.rainbow.sdk.analytics.event.buz.THPageEvent;

/**
 * Created by bvin on 2016/8/12.
 */
public class LocalReporter{

    private static final long INTERVAL = 1000 * 10;
    private List<AbsEventTable> list = new ArrayList<>();
    private Handler mHandler;
    private EventRunnable mEventRunnable;

    public LocalReporter(Context context) {
        list.add(new THPageTable(context));
        list.add(new GoodsTable(context));
        list.add(new CartTable(context));
        list.add(new FavTable(context));
        list.add(new OrderTable(context));
        list.add(new THEventTable(context));
        mHandler = new Handler();
        mEventRunnable = new EventRunnable();
    }

    public void report(){
        reportEvents(list);
    }

    private void reportEvents(List<AbsEventTable> tables) {
        for (AbsEventTable table : tables) {
            reportTable(table);
        }
    }

    private void reportTable(AbsEventTable table) {
        List<Event> list = table.query();
        if (list == null || list.isEmpty()) return;
        for (Event event : list) {
            mEventRunnable.setEvent(event, table);
            mHandler.postDelayed(mEventRunnable, INTERVAL);
        }
    }

    private void reportEvents(AbsEventTable table, Event event) {
        if (event instanceof THPageEvent){
            if (event instanceof GoodsViewEvent){
                new GpvReporter((GoodsViewEvent) event).push(callback(event, table));
            }else {
                new ApvReporter((THPageEvent) event).push(callback(event, table));
            }
        }else if (event instanceof CartEvent){
            new CartReporter((CartEvent) event).push(callback(event, table));
        }else if (event instanceof FavoriteEvent){
            new FavReporter((FavoriteEvent) event).push(callback(event, table));
        }else if (event instanceof OrderEvent){
            new OrderReporter((OrderEvent) event).push(callback(event, table));
        }else if (event instanceof THEvent){
            new THEventReport((THEvent) event).push(callback(event, table));
        }
    }

    private Callback<Model> callback(final Event event, final AbsEventTable table) {
        return new Callback<Model>() {
            @Override
            public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
                if (model != null && model.getRet() == 200) {
                    table.delete(event);
                }
            }

            @Override
            public void onFailed(Request request, Exception e) {

            }
        };
    }

    class EventRunnable implements Runnable{

        private AbsEventTable table;
        private Event event;

        public void setEvent(Event event, AbsEventTable table) {
            this.event = event;
            this.table = table;
        }

        @Override
        public void run() {
            reportEvents(table, event);
        }
    }
}
