package cn.rainbow.sdk.analytics.event;

/**
 * Created by 32967 on 2016/5/31.
 * 页面访问事件.
 */
public class PageEvent extends Event{
    public static final long EVENT_ID = 1;

    private String mCurrentPage;
    private String mPreviousPage;

    public PageEvent(String page) {
        super(EVENT_ID);
        mCurrentPage = page;
    }

    public void setPreviousPage(String previousPage) {
        mPreviousPage = previousPage;
    }
}
