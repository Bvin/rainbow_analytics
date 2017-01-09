package cn.rainbow.sdk.thanalytics;

import cn.rainbow.sdk.analytics.event.Event;

/**
 * Created by bvin on 2017/1/9.
 */

public class TestEvent extends Event {

    public TestEvent() {
        super("/report_events_user");
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
