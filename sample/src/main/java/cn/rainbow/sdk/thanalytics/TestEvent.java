package cn.rainbow.sdk.thanalytics;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2017/1/9.
 */

public class TestEvent extends Event {

    private static final String EVENT_NAME = "TestEvent";

    public TestEvent() {
        super(EVENT_NAME);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getName());
        sb.append("?");
        putValue(sb, "c", String.valueOf(mChannelId));
        putValue(sb, "tn", "1");
        return sb.toString();
    }
}
