package cn.rainbow.sdk.analytics.track;

import android.text.TextUtils;

import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.event.PageEvent;

/**
 * Created by 32967 on 2016/5/31.
 */
public class PageTracker extends  EventTracker{

    private String mPageName;
    private PageEvent mPageEvent;

    public PageTracker(String pageName) {
        super(PageEvent.EVENT_ID, "统计页面");
        mPageName = pageName;
    }

    @Override
    public Event createEvent() {
        return mPageEvent;
    }

    /**
     * 页面开始统计
     * @param previousPage 前一页
     */
    public void onPageStart(String previousPage){
        if (mPageEvent == null) {
            mPageEvent = new PageEvent(mPageName);
        }
        if (TextUtils.isEmpty(previousPage)) {
            mPageEvent.setPreviousPage(previousPage);
        }
        onEventStart();
    }

    public void onPageEnd(){
        onEventEnd();
    }
}
