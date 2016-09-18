package cn.rainbow.sdk.analytics.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.SparseArray;

import java.util.HashMap;

import cn.rainbow.sdk.analytics.api.Api;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

/**
 * Created by bvin on 2016/9/14.
 */
public class Tracker {

    public static final String TH_CHANNEL = "TH_CHANNEL";

    private Context mContext;
    private Config mConfig;

    private String mBaseUrl;

    public Tracker(Context context, Config config) {
        mContext = context;
        mConfig = config;
        if (config.isTestEnv()) {
            mBaseUrl = Api.HOST_TEST + Api.URL_REPORT;
        } else {
            mBaseUrl = Api.HOST_OFFICIAL + Api.URL_REPORT;
        }
    }

    public int getMetaDataIntValue(String name) {
        PackageManager localPackageManager = mContext.getPackageManager();
        try {
            ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            if (localApplicationInfo != null) {
                return localApplicationInfo.metaData.getInt(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.MIN_VALUE;
    }

    public int getChannelId(){
        return getMetaDataIntValue(TH_CHANNEL);
    }

    public void track(Event event){
        if (!mConfig.isEnable()) return;
        if (event != null) {
            String eventBody = event.toPersistableString();
            if (!TextUtils.isEmpty(eventBody)) {
                if (mConfig.isRealTime()) {
                    TransportService.startFromCurrent(mContext,mBaseUrl,eventBody);
                }else {
                    PersistenceService.getInstance(mContext).save(event);
                }
            }
        }
    }

    //推送本地数据
    public void reportLocal(){
        if (!mConfig.isEnable()) return;
        //一次最多只能推50条
        PersistenceService.getInstance(mContext).query(new PersistenceService.SQLCallback<SparseArray<String>>() {
            @Override
            public void callback(SparseArray<String> stringSparseArray) {
                HashMap<Integer, String> data = new HashMap<>();
                for (int i = 0; i < stringSparseArray.size(); i++) {
                    Integer key = stringSparseArray.keyAt(i);
                    data.put(key, stringSparseArray.get(key));
                }
                if (!data.isEmpty()) {
                    TransportService.startFromLocal(mContext, mBaseUrl, data);
                }
            }
        });
    }

}
