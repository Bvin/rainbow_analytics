package cn.rainbow.sdk.analytics.core;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.rainbow.sdk.analytics.api.Api;
import cn.rainbow.sdk.analytics.api.Model;
import cn.rainbow.sdk.analytics.api.ModelReader;
import cn.rainbow.sdk.analytics.net.Request;
import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.reader.ResponseReader;
import cn.rainbow.sdk.analytics.persistence.Persistable;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;
import cn.rainbow.sdk.analytics.persistence.Record;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class TransportService extends IntentService {

    private static final String TAG = "THAnalytics";

    private static final String ACTION_PUSH_LOCAL = "ACTION_PUSH_LOCAL";
    private static final String ACTION_PUSH_CURRENT = "ACTION_PUSH_CURRENT";
    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_LOCAL_DATA = "LOCAL_DATA";
    private static final String EXTRA_CURRENT_DATA = "CURRENT_DATA";
    private static final String EXTRA_ROW_ID = "EXTRA_ROW_ID";
    private static final String EXTRA_TASK_INTERVAL = "EXTRA_TASK_INTERVAL";
    private static final long TASK_INTERVAL = 1500;//任务间隔时间

    public static final int MESSAGE_DB_DELETE = 1;
    public static final int MESSAGE_DB_RELEASE = 0;

    /**
     * 批量上报
     * @param context 上下文
     * @param url url
     * @param eventList 事件列表
     * @param interval 任务间隔时间
     */
    public static void startReport(Context context, String url, ArrayList<Record> eventList, long interval) {
        Intent intent = new Intent(context, TransportService.class);
        intent.setAction(ACTION_PUSH_LOCAL);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_LOCAL_DATA, eventList);
        intent.putExtra(EXTRA_TASK_INTERVAL, interval);
        context.startService(intent);
    }

    /**
     * 发送事件.
     * @param context 上下文
     * @param url url
     * @param data 数据
     * @param rowId 数据库rowId
     * @param interval 任务间隔时间
     */
    private static void startReport(Context context, String url, String data, int rowId, long interval) {
        Intent intent = new Intent(context, TransportService.class);
        intent.setAction(ACTION_PUSH_CURRENT);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_CURRENT_DATA, data);
        intent.putExtra(EXTRA_ROW_ID, rowId);
        intent.putExtra(EXTRA_TASK_INTERVAL, interval);
        context.startService(intent);
    }

    /**
     * 单次上报.
     * @param context 上下文
     * @param url url
     * @param data 数据
     */
    public static void startReport(Context context, String url, String data) {
        startReport(context, url, data, -1, 0);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_DB_RELEASE:
                    PersistenceService.getInstance(getApplicationContext()).end();//上传完
                    mEnd = true;
                    log("ReportTask", "complete");
                    break;
                case MESSAGE_DB_DELETE:
                    PersistenceService.getInstance(getApplicationContext()).delete(msg.arg1);//删除数据库记录
                    log("DeleteRecord", String.valueOf(msg.arg1));//不一定删除成功...
                    break;
            }

        }
    };

    private long mTaskInterval;
    private boolean mEnd;// 批量推送结束，每次启动只推送一次

    public TransportService() {
        super("TransportThread");//WorkThread-Name
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            mTaskInterval = intent.getLongExtra(EXTRA_TASK_INTERVAL, TASK_INTERVAL);
            if (ACTION_PUSH_LOCAL.equals(action) && !mEnd) {
                final String url = intent.getStringExtra(EXTRA_URL);
                final ArrayList<Record> data = (ArrayList<Record>) intent.getSerializableExtra(EXTRA_LOCAL_DATA);
                handleFromLocal(url, data);
            }else if(ACTION_PUSH_CURRENT.equals(action)){
                final String url = intent.getStringExtra(EXTRA_URL);
                String data = intent.getStringExtra(EXTRA_CURRENT_DATA);
                int rowId = intent.getIntExtra(EXTRA_ROW_ID, -1);
                handleFromCurrent(url, data, rowId);
            }
        }
    }

    //批量递归任务
    private void handleFromLocal(final String url, final LinkedHashMap<Integer,String> data) {
        LinkedHashMap.Entry<Integer,String> entry = obtainEntry(data);
        if (entry != null) {
            Response<Model> response = performRequest(buildUrl(url, entry.getValue()), entry.getKey());
            boolean isSuccess = handleResponse(response);
            if (isSuccess) {
                // 删除本地记录
                Message message = mHandler.obtainMessage();
                message.what = MESSAGE_DB_DELETE;
                message.arg1 = entry.getKey();
                mHandler.sendMessage(message);
            } else {
                //失败就不管了，下次再推
            }
            data.remove(entry.getKey());//移除处理过的任务
            sleepThread();
            handleFromLocal(url, data);//递归
        }else {
            Message message = mHandler.obtainMessage();
            message.what = MESSAGE_DB_RELEASE;
            mHandler.sendMessage(message);
        }
    }

    // 批量循环任务
    private void handleFromLocal(final String url, final ArrayList<Record> data){
        for (Record record : data) {
            handleFromCurrent(url, record.getBody(), record.getRowId());
            sleepThread();// 任务间隔
        }
        // 释放数据库
        Message message = mHandler.obtainMessage();
        message.what = MESSAGE_DB_RELEASE;
        mHandler.sendMessage(message);
    }

    //单独任务
    private void handleFromCurrent(final String url, String data, int rowId){
        Response<Model> response = performRequest(buildUrl(url, data), rowId);
        boolean isSuccess = handleResponse(response);
        if (isSuccess){
            // 删除本地记录
            if (rowId >= 0) {
                Message message = mHandler.obtainMessage();
                message.what = MESSAGE_DB_DELETE;
                message.arg1 = rowId;
                mHandler.sendMessage(message);
            }
        }else {
            // 如果是从数据库读取的事件，失败了保存到本地，待下次再推
            if (rowId < 0) PersistenceService.getInstance(this).save(new Data(data));
        }
    }


    private String buildUrl(String host, String data) {
        if (data.startsWith("/")) {//有/一定要跟?
            return host + data.substring(1);
        } else {
            return host + Api.URL_REPORT + "?" + data;
        }
    }


    private void log(String tag, String message) {
        if (TextUtils.isEmpty(message)) message = "empty content";
        Log.d(TAG + "-" + tag, message);
    }

    class Data implements Persistable{

        private String data;

        public Data(String data) {
            this.data = data;
        }

        @Override
        public String toPersistableString() {
            return data;
        }
    }

    //执行请求(GET)
    private Response<Model> performRequest(String completionUrl, int rowId) {
        Request<Model> request = new Request<>();
        ResponseReader<Model> read = new ModelReader();
        log("RequestStart("+rowId+")", completionUrl);
        return request.performGet(completionUrl,read);
    }

    private boolean handleResponse(Response<Model> response){
        if (response != null) {
            if (response.isSuccess()) {//HTTP访问成功
                Model model = response.get();
                if (model != null) {
                    log("RequestResponse", model.getOriginResponse());
                    if (model.isOkay()) {//服务端返回成功
                        return true;
                    } else {
                        //失败就不管了，下次再推
                    }
                }else {
                    log("RequestError: ", "no response target");
                }
            } else {
                log("RequestError: ", "http status code is "+response.getNetworkResponse().getResponseCode());
            }
        }else {
            log("RequestError: ", "no response");
        }
        return false;
    }

    //休眠当前线程，达到延迟执行效果
    private void sleepThread() {
        try {
            log("SleepThread(" + mTaskInterval + "ms)", Thread.currentThread().getName());
            Thread.sleep(mTaskInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private LinkedHashMap.Entry<Integer,String> obtainEntry(LinkedHashMap<Integer, String> data) {
        for (LinkedHashMap.Entry<Integer,String> entry :data.entrySet()){
            return entry;
        }
        return null;
    }

}
