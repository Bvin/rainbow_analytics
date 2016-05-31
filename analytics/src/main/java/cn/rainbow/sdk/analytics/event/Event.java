package cn.rainbow.sdk.analytics.event;

/**
 * Created by 32967 on 2016/5/31.
 */
public class Event {

    private long mEventId;
    private String mEventName;
    private String mEventDesc;
    private int mEventType;
    private String mStartDate;
    private String mEndDate;
    private long mDuration;

    public Event(long eventId) {
        mEventId = eventId;
    }

    public Event(long eventId, String eventName, String eventDesc) {
        mEventId = eventId;
        mEventName = eventName;
        mEventDesc = eventDesc;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getEventId() {
        return mEventId;
    }

    public String getEventName() {
        return mEventName;
    }

    public String getEventDesc() {
        return mEventDesc;
    }

    public int getEventType() {
        return mEventType;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

}
