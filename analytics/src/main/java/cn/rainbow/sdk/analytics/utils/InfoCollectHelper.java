package cn.rainbow.sdk.analytics.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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

    /**
     * 获取设备的UUID
     *
     * @return 获取设备的UUID
     */
    public String getDeviceUUID() {
        final TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, androidId;
        tmDevice = "" + tm.getDeviceId();
        androidId = ""
                + android.provider.Settings.Secure.getString(mContext.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32));
        String uniqueId = md5(deviceUuid.toString());
        return uniqueId;
    }

    /** MD5 encrypt */
    public static String md5(String str) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(str.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0; i < arrayOfByte.length; i++) {
                int j = 0xFF & arrayOfByte[i];
                if (j < 16)
                    localStringBuffer.append("0");
                localStringBuffer.append(Integer.toHexString(j));
            }
            return localStringBuffer.toString();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            Log.e("MD5Utility", "getMD5 error");
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }
}
