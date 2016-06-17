package cn.rainbow.sdk.analytics.track;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.PageTable;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.event.PageEvent;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageTracker extends AbsEventTracker<PageEvent> {

    private static final String TAG = "PageTracker";

    private String mPageName;
    private PageEvent mPageEvent;
    private PageTable mPageTable;

    public PageTracker(Context context) {
        super(PageEvent.EVENT_ID, "统计页面");
        attachContext(context);
        mPageName = context.getClass().getName();
    }

    /**
     * 页面开始统计.
     */
    public void onPageStart(){
        onPageStartAfter(null);
    }

    /**
     * 页面开始统计.
     * @param previousPage 前一页
     */
    public void onPageStartAfter(String previousPage){
        if (mPageEvent == null) {//必须在onEventStart()前初始化
            mPageEvent = new PageEvent(mPageName);
        }
        if (!TextUtils.isEmpty(previousPage)) {
            mPageEvent.setPreviousPage(previousPage);
        }
        onEventStart();
    }

    public void onPageEnd()throws IllegalStateException{
        onEventEnd();//一定要调用onEventEnd()才会完成统计并且保存到数据库
    }

    @Override
    public PageEvent createEvent() {
        return mPageEvent;
    }

    @Override
    public SQLTable createTable(PageEvent event, SQLiteDatabase database) {
        if (mPageTable == null) {
            mPageTable = new PageTable(database);
        }
        return mPageTable;
    }

}
