#移动统计SDK

 --- 
 
###App生命周期统计：
* public static void onAppStart(Context context)
* public static void onAppExit()

###页面周期统计：
* public static void onResume(Context context)
* public static void onPause(Context context)

###配置
```java
Config config = new Config();
config.enableDebugLog(true);//打开log
config.enableCrashTrack(true);//开启崩溃收集
config.setTestEnv(false);
THAnalytics.setConfig(config);
```


