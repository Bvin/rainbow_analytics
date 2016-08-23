package cn.rainbow.sdk.analytics.data.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bvin on 2016/6/12.
 */
public class Model {

    /**
     * ret : 200
     * message : success
     */

    @SerializedName("ret")
    private int mRet;

    @SerializedName("message")
    private String mMessage;

    public int getRet() {
        return mRet;
    }

    public boolean isOK(){
        return mRet == 200;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mRet=" + mRet +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}
