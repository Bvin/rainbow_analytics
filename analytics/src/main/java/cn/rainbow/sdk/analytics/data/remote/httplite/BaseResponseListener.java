package cn.rainbow.sdk.analytics.data.remote.httplite;

import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import cn.rainbow.sdk.analytics.data.remote.Model;

/**
 * Created by bvin on 2016/8/30.
 */
public class BaseResponseListener extends HttpListener<Model>{

    private static final String TAG = "THAnalytics";

    private HttpListener<Model> mCallback;
    private boolean mPrintRequest;

    public BaseResponseListener(HttpListener<Model> callback, boolean printRequest) {
        mCallback = callback;
        mPrintRequest = printRequest;
    }

    public BaseResponseListener(HttpListener<Model> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(Model model, Response<Model> response) {
        super.onSuccess(model, response);
    }

    @Override
    public void onFailure(HttpException e, Response<Model> response) {
        super.onFailure(e, response);
    }
}
