package cn.rainbow.sdk.analytics.core;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import cn.rainbow.sdk.analytics.api.Model;
import cn.rainbow.sdk.analytics.api.ModelReader;
import cn.rainbow.sdk.analytics.net.Request;
import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.reader.ResponseReader;
import cn.rainbow.sdk.analytics.persistence.Persistable;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

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
    private static final long TASK_INTERVAL = 1500;//任务间隔时间

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context 上下文
     * @param url url
     * @param map 本地数据
     */
    public static void startFromLocal(Context context, String url, HashMap<Integer,String> map) {
        Intent intent = new Intent(context, TransportService.class);
        intent.setAction(ACTION_PUSH_LOCAL);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_LOCAL_DATA, map);
        context.startService(intent);
    }

    /**
     * 即时推送.
     * @param context 上下文
     * @param url url
     * @param data 数据
     */
    public static void startFromCurrent(Context context, String url, String data) {
        Intent intent = new Intent(context, TransportService.class);
        intent.setAction(ACTION_PUSH_CURRENT);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_CURRENT_DATA, data);
        context.startService(intent);
    }

    public TransportService() {
        super("TransportThread");//WorkThread-Name
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PUSH_LOCAL.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                final HashMap<Integer,String> data = (HashMap<Integer, String>) intent.getSerializableExtra(EXTRA_LOCAL_DATA);
                handleFromLocal(url, data);
            }else if(ACTION_PUSH_CURRENT.equals(action)){
                final String url = intent.getStringExtra(EXTRA_URL);
                String data = intent.getStringExtra(EXTRA_CURRENT_DATA);
                handleFromCurrent(url,data);
            }
        }
    }

    //批量递归任务
    private void handleFromLocal(final String url, final HashMap<Integer,String> data) {
        HashMap.Entry<Integer,String> entry = obtainEntry(data);
        if (entry != null) {
            String completionUrl = url+"?"+entry.getValue();//完整的url
            Response<Model> response = performRequest(completionUrl);
            boolean isSuccess = handleResponse(response);
            if (isSuccess) {
                PersistenceService.getInstance(this).delete(entry.getKey());//删除数据库记录
                log("DeleteRecord", String.valueOf(entry.getKey()));
                data.remove(entry.getKey());//移除处理过的任务
            } else {
                //失败就不管了，下次再推
            }
            sleepThread();
            handleFromLocal(url, data);//递归
        }else {
            PersistenceService.getInstance(this).end();//上传完
        }
    }

    //单独任务
    private void handleFromCurrent(final String url, String data){
        String completionUrl = url+"?"+data;//完整的url
        Response<Model> response = performRequest(completionUrl);
        boolean isSuccess = handleResponse(response);
        if (isSuccess){
            //成功就不管了
        }else {
            PersistenceService.getInstance(this).save(new Data(data));
            //失败了保存到本地，待下次再推
        }
        //sleepThread();//是否需要延迟线程？
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
    private Response<Model> performRequest(String completionUrl) {
        Request<Model> request = new Request<>();
        ResponseReader<Model> read = new ModelReader();
        log("RequestStart", completionUrl);
        return request.performGet(completionUrl,read);
    }

    private boolean handleResponse(Response<Model> response){
        if (response != null) {
            if (response.isSuccess()) {//HTTP访问成功
                Model model = response.get();
                log("RequestResponse", model.getOriginResponse());
                if (model.isOkay()) {//服务端返回成功
                    return true;
                } else {
                    //失败就不管了，下次再推
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
            Thread.sleep(TASK_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private HashMap.Entry<Integer,String> obtainEntry(HashMap<Integer, String> data) {
        for (HashMap.Entry<Integer,String> entry :data.entrySet()){
            return entry;
        }
        return null;
    }

}
