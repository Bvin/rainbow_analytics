package cn.rainbow.sdk.analytics.event;

import android.content.ContentValues;
import android.text.TextUtils;

/**
 * Created by bvin on 2016/5/31.
 * 页面访问事件.
 */
public class PageEvent extends Event{
    public static final long EVENT_ID = 1;

    private String mCurrentPage;
    private String mPreviousPage;

    public PageEvent() {
        //empty construct,just use to create sql table when DBHelper create.
    }

    public PageEvent(String page) {
        super(EVENT_ID);
        mCurrentPage = page;
    }

    public void setPreviousPage(String previousPage) {
        mPreviousPage = previousPage;
    }

    @Override
    public String tableName() {
        return "pages";
    }

    @Override
    public String defineFields() {
        return Columns.PAGE + " TEXT," +
                Columns.PREVIOUS_PAGE + " TEXT," +
                Event.Columns.EVENT_START_DATE + " TEXT," +
                Event.Columns.EVENT_END_DATE + " TEXT," +
                Event.Columns.EVENT_DURATION + " LONG";
    }

    @Override
    public ContentValues values() {
        ContentValues cv = new ContentValues();
        if (!TextUtils.isEmpty(mCurrentPage)) cv.put(Columns.PAGE, mCurrentPage);
        if (!TextUtils.isEmpty(mPreviousPage)) cv.put(Columns.PREVIOUS_PAGE, mPreviousPage);
        if (!TextUtils.isEmpty(mStartDate)) cv.put(Event.Columns.EVENT_START_DATE, mStartDate);
        if (!TextUtils.isEmpty(mEndDate)) cv.put(Event.Columns.EVENT_END_DATE, mEndDate);
        if (mDuration > 0) cv.put(Event.Columns.EVENT_DURATION, mDuration);
        return cv;
    }

    class Columns{
        public static final String PAGE = "page";
        public static final String PREVIOUS_PAGE = "previous_page";
    }
}
