# Android Analytics SDK
这是一个简单易用的Android统计库，只需要调用几个方法便可完成数据统计和上报。

 --- 
 
 
### 引入
添加以下依赖到build.gradle文件,在project的build.gradle中需要添加jitpack的远程仓库定义。

dependencies {
    ...
    compile 'com.github.bvin.rainbow_analytics:analytics_core:3.6.3'
}
 

### 配置
```java
Config config = new Config();
config.setEnable(true);// 统计开关
config.setRealTime(true);// 实时上报
config.setTaskInterval(2500);// 上报间隔时间
THAnalytics.init(content, config);// 初始化
```

### 功能
1. 使用 THAnalytics.track(event) 来统计一个事件，如果配置打开统计功能。如果打开了实时上报功能将会即刻上报，否则将会存到本地数据库待下次上传。如果上报失败了也会保存到本地数据库。
 
2. 使用 THAnalytics.reportLocal() 方法来上报本地数据库保存的事件，如果配置打开统计功能。这些保存在数据库的事件将会一个接一个上传，但最多一次性取50条记录从数据库。

### 扩展
你可以通过继承Event类来创建自定义事件，只需要覆写toString()方法来定义事件的具体数据。如:
 
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