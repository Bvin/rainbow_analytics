package cn.rainbow.sdk.analytics.net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by bvin on 2016/8/18.
 */
public class RequestBody {

    private String mContentType;
    private long mContentLength;
    private byte[] mContent;

    public RequestBody(byte[] content, String contentType) {
        mContent = content;
        mContentType = contentType;
        mContentLength = mContent.length;
    }


}
