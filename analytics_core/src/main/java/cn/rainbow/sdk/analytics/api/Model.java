package cn.rainbow.sdk.analytics.api;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bvin on 2016/9/14.
 */
public class Model {
    private static final String KEY_CODE = "ret";
    private static final String KEY_MESSAGE = "message";
    private static final int STATUS_OK = 200;
    private int mCode;
    private String mMessage;

    private String mOriginResponse;

    public Model(String json) throws JSONException {
        this(new JSONObject(json));
        mOriginResponse = json;
    }

    public Model(JSONObject jsonObject) {
        if (jsonObject != null) {
            if(jsonObject.has(KEY_CODE)){
                try {
                    mCode = jsonObject.getInt(KEY_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jsonObject.has(KEY_MESSAGE)){
                try {
                    mMessage = jsonObject.getString(KEY_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getOriginResponse() {
        return mOriginResponse;
    }

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isOkay(){
        return mCode == STATUS_OK;
    }
}
