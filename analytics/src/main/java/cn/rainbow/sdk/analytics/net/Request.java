package cn.rainbow.sdk.analytics.net;

import android.os.Build;
import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import cn.rainbow.sdk.analytics.net.progress.ProgressListener;
import cn.rainbow.sdk.analytics.net.progress.ProgressOutputStream;
import cn.rainbow.sdk.analytics.net.response.NetworkResponse;
import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.ResponseReader;

/**
 * Created by bvin on 2016/8/18.
 */
public class Request<T> {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String OPTIONS = "OPTIONS";
    public static final String HEAD = "HEAD";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String TRACE = "TRACE";

    private volatile boolean isCanceled;

    public Response<T> perform(String url, String method, Map<String, String> headers, byte[] content, String contentType,
                              ProgressListener progressListener, ResponseReader<T> responseReader){
        HttpURLConnection conn = null;
        try {
            //1.Obtain HttpURLConnection
            conn = (HttpURLConnection) new URL(url).openConnection();
            //2.prepare request
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            //3.transmit data
            if (isOutputMethod(method)) {
                doOutput(conn, content, contentType, progressListener);
            }
            //4.read response
            NetworkResponse networkResponse = new NetworkResponse(conn.getInputStream(), conn.getResponseCode(), conn.getResponseMessage());
            Response<T> response = new Response<>(networkResponse);
            response.setReader(responseReader);
            return response;

        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            //5.disconnect
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

    private void doOutput(HttpURLConnection conn, byte[] content, String contentType, ProgressListener progressListener) throws IOException {
        conn.setDoOutput(true);
        if (content != null) {
            long contentLength = content.length;
            if (contentLength < 0) {
                conn.setChunkedStreamingMode(256 * 1024);
            } else {
                if (contentLength < Integer.MAX_VALUE) {
                    conn.setFixedLengthStreamingMode((int) contentLength);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    conn.setFixedLengthStreamingMode(contentLength);
                } else {
                    conn.setChunkedStreamingMode(256 * 1024);
                }
            }
            conn.setRequestProperty("Content-Length", String.valueOf(content.length));
            if (!TextUtils.isEmpty(contentType)) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            if (progressListener != null) {
                ProgressOutputStream progressOutputStream =
                        new ProgressOutputStream(conn.getOutputStream(), progressListener);
                progressOutputStream.write(content);
            } else {
                conn.getOutputStream().write(content);
            }
        }
    }

    private boolean isOutputMethod(String method) {
        return method.equalsIgnoreCase(POST)
                || method.equalsIgnoreCase(PUT);
    }

    public boolean isCanceled() {
        return isCanceled;
    }
}
