package cn.rainbow.sdk.analytics.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by bvin on 2016/6/12.
 */
public class InfoCollectHelper {

    private Context mContext;

    public InfoCollectHelper(Context context) {
        mContext = context;
    }

    /**
     * checkPermissions
     *
     * @param permission
     * @return true or false
     */
    public boolean checkPermissions(String permission) {
        PackageManager localPackageManager = mContext.getPackageManager();
        return localPackageManager.checkPermission(permission, mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * get MetaData VALUE
     *
     * @return appkey
     */
    private String getMetaDataValue(String name) {
        String appkey;
        try {
            PackageManager localPackageManager = mContext.getPackageManager();
            ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            if (localApplicationInfo != null) {
                String str = localApplicationInfo.metaData.getString(name);
                if (str != null) {
                    appkey = str;
                    return appkey.toString();
                }
                Log.e("getMetaDataValue", "Could not read " + name + " meta-data from AndroidManifest.xml.");
            }
        } catch (Exception localException) {
            Log.e("getMetaDataValue", "Could not read " + name + " meta-data from AndroidManifest.xml.");
            localException.printStackTrace();
        }
        return "";
    }

    public String getDeviceID() {
        if (mContext == null) {
            return "";
        }
        if (checkPermissions("android.permission.READ_PHONE_STATE")) {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            String backId = "";
            if (deviceId != null) {
                backId = new String(deviceId);
                backId = backId.replace("0", "");
            }

            if (((TextUtils.isEmpty(deviceId)) || TextUtils.isEmpty(backId)) && (Build.VERSION.SDK_INT >= 9)) {
                Log.e("getDeviceID is null ==>>", " getting from the permission READ_PHONE_STATE ==>>");
                try {
                    Class c = Class.forName("android.os.SystemProperties");
                    Method get = c.getMethod("get", new Class[] { String.class, String.class });
                    deviceId = (String) get.invoke(c, new Object[] { "ro.serialno", "unknown" });
                } catch (Exception t) {
                    Log.e("getDeviceID ==>>", " deviceid is null ==>>", t);
                    deviceId = null;
                }
            }

            if (!TextUtils.isEmpty(deviceId)) {
                Log.w("getDeviceID", "deviceId:" + deviceId);
                return deviceId;
            } else {
                Log.e("getDeviceID", "deviceId is null");
                return "";
            }
        } else {
            Log.e("lost permissioin", "lost----->android.permission.READ_PHONE_STATE");
            return "";
        }
    }

}
