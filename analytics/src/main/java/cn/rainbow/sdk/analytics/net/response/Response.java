package cn.rainbow.sdk.analytics.net.response;

import java.io.IOException;

/**
 * Created by bvin on 2016/8/19.
 */
public class Response<T> {
    private T mResult;
    private NetworkResponse mNetworkResponse;

    public Response(NetworkResponse networkResponse) {
        mNetworkResponse = networkResponse;
    }

    public void setReader(ResponseReader<T> reader) {
        try {
            mResult = reader.read(mNetworkResponse.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T get(){
        return mResult;
    }

    public NetworkResponse getNetworkResponse() {
        return mNetworkResponse;
    }

    public boolean isSuccess(){
        if (mNetworkResponse != null) {
            return mNetworkResponse.isResponseOK();
        }
        return false;
    }
}
