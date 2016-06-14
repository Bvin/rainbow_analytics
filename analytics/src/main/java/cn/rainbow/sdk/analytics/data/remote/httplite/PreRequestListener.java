package cn.rainbow.sdk.analytics.data.remote.httplite;

import android.util.Log;

import java.lang.reflect.Type;

import alexclin.httplite.HttpLite;
import alexclin.httplite.Request;
import alexclin.httplite.listener.RequestListener;

/**
 * Created by bvin on 2016/6/14.
 */
public class PreRequestListener implements RequestListener {

    private static final String TAG = "THAnalytics";

    @Override
    public void onRequest(HttpLite httpLite, Request request, Type type) {
        Log.d(TAG, "start-report ==> "+request.getUrl());
    }
}
