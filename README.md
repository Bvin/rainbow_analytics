# Android Analytics SDK
This Library is a easy analytics lib,it use very simple only call several method to complete data
analytics and report.

 --- 
 
 
### Import 
add dependency with bellow line,and add jitpack maven repo define.
```
dependencies {
    ...
    compile 'com.github.bvin.rainbow_analytics:analytics_core:3.6.3'
}
```
 

### Config
```java
Config config = new Config();
config.setEnable(true);// 统计开关
config.setRealTime(true);// 实时上报
config.setTaskInterval(2500);// 上报间隔时间
THAnalytics.init(content, config);// 初始化
```

### Feature
1. Use THAnalytics.track(event) to track a event,if the config is enable.
if config real-time will report currently,else will save on local database.
and report failed it will save on local database yet.
 
2. Use THAnalytics.reportLocal() to report the events on local database,
if the config is enable.The event record will report one by one, but most
50 record invoke once.

### Extend
You can extend the Event class create yourself customer event, only override the
 toString() method to record your event data body.Like this:
 
```java
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
```

