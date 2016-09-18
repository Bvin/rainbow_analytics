package cn.bvin.lib.coretest;

import cn.rainbow.sdk.analytics.persistence.Persistable;

/**
 * Created by bvin on 2016/9/14.
 */
public class Event implements Persistable{

    private String mEventName;

    public Event(String eventName) {
        mEventName = eventName;
    }

    @Override
    public String toPersistableString() {
        return toString();
    }
}
