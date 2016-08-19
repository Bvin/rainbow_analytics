package cn.rainbow.sdk.analytics.net.response;

import java.io.InputStream;

/**
 * Created by bvin on 2016/8/19.
 */
public class NetworkResponse {

    private long mContentLength;
    private InputStream mContent;
    private int mResponseCode;
    private String mResponseMessage;

    public NetworkResponse(InputStream content, int responseCode, String responseMessage) {
        mContent = content;
        mResponseCode = responseCode;
        mResponseMessage = responseMessage;
    }

    public InputStream getContent() {
        return mContent;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public boolean isResponseOK(){
        return mResponseCode == 200;
    }
}
