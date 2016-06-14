package cn.rainbow.sdk.analytics.data.remote.httplite;


import java.util.List;
import java.util.Map;

import alexclin.httplite.Request;
import alexclin.httplite.listener.Callback;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * Created by bvin on 2016/6/14.
 */
public class BaseResponseCallback implements Callback<Model> {

    private static final String TAG = "THAnalytics";

    private Callback<Model> mCallback;
    private boolean mPrintRequest;

    public BaseResponseCallback() {
    }

    /**
     * BaseResponseCallback，回掉时做一些统一处理.
     * @param callback callback
     */
    public BaseResponseCallback(Callback<Model> callback) {
        mCallback = callback;
    }

    /**
     * BaseResponseCallback，回掉时做一些统一处理.
     * @param callback callback
     * @param printRequest 是否打印请求（开始请求和得到响应的打印是分开，有可能导致混乱很难对比，
     *                     设置为true可以追溯情趣地址）
     */
    public BaseResponseCallback(Callback<Model> callback, boolean printRequest) {
        mCallback = callback;
        mPrintRequest = printRequest;
    }

    @Override
    public void onSuccess(Request request, Map<String, List<String>> map, Model model) {
        if (model != null) {
            if (mPrintRequest){
                Log.d(TAG, "report-success==> " + model.toString()+ "\nreport-url==>"+request.getUrl());
            }else {
                Log.d(TAG, "report-success==> " + model.toString());
            }

            if (model.getRet() == 200) {//服务端
                //请求成功
            } else {
                //失败
            }
        }
        if (mCallback != null) {
            mCallback.onSuccess(request, map, model);
        }

    }

    @Override
    public void onFailed(Request request, Exception e) {
        Log.e(TAG, "report-failed: " + request.rawUrl(), e);
        if (mCallback != null) {
            mCallback.onFailed(request, e);
        }
    }
}
