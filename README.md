# 移动统计SDK

 --- 
 
### App生命周期统计：
* public static void onAppStart(Context context)
* public static void onAppExit()

### 页面周期统计：
* public static void onResume(Context context)
* public static void onPause(Context context)

### 配置
```java
Config config = new Config();
config.enableDebugLog(true);//打开log
config.enableCrashTrack(true);//开启崩溃收集
config.setTestEnv(false);
config.setPushRemote(true);//设置是否上报到服务器
config.setSaveLocal(true);//设置是否保存到本地
config.setPushStrategy(Config.PUSH_STRATEGY_REAL_TIME);//设置上报策略
//启动时批量发送：PUSH_STRATEGY_BATCH_BOOTSTRAP = 0;
//实时发送：PUSH_STRATEGY_REAL_TIME = 1;
THAnalytics.setConfig(config);
```

### 渠道
```xml
<meta-data android:name="TH_CHANNEL"  android:value="2006" />
```


