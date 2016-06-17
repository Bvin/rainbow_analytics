package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.db.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.PageTable;

/**
 * Created by bvin on 2016/5/31.
 * 页面访问事件.
 */
public class PageEvent extends Event{
    public static final long EVENT_ID = 2;

    private String mCurrentPage;
    private String mPreviousPage;

    public PageEvent() {
        //empty construct,just use to create sql table when DBHelper create.
    }

    public PageEvent(String page) {
        super(EVENT_ID);
        mCurrentPage = page;
    }

    public PageEvent(Cursor cursor) {
        super(cursor);
    }

    public void setPreviousPage(String previousPage) {
        mPreviousPage = previousPage;
    }

    @Override
    public ContentValues saveValues() {
        ContentValues cv = new ContentValues();
        if (!TextUtils.isEmpty(mCurrentPage)) cv.put(PageTable.Columns.PAGE, mCurrentPage);
        if (!TextUtils.isEmpty(mPreviousPage)) cv.put(PageTable.Columns.PREVIOUS_PAGE, mPreviousPage);
        if (!TextUtils.isEmpty(mStartDate)) cv.put(EventTable.Columns.EVENT_START_DATE, mStartDate);
        if (!TextUtils.isEmpty(mEndDate)) cv.put(EventTable.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(EventTable.Columns.EVENT_DURATION, mDuration);
        return cv;
    }
}
