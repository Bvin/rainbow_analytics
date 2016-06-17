package cn.rainbow.sdk.analytics.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by bvin on 2016/6/16.
 */
public class NetworkHelper {

    private Context mContext;

    public NetworkHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean inWifiNetwork() {
        ConnectivityManager connectionManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectionManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }
}
