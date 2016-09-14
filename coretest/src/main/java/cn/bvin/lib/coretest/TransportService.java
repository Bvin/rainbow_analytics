package cn.bvin.lib.coretest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;

import cn.rainbow.sdk.analytics.net.Request;
import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.reader.ResponseReader;
import cn.rainbow.sdk.analytics.net.response.reader.StringResponseReader;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class TransportService extends IntentService {

    private static final String ACTION_PUSH_LOCAL = "ACTION_PUSH_LOCAL";
    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_LOCAL_DATA = "LOCAL_DATA";

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

    public TransportService() {
        super("TransportService");//WorkThread-Name
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PUSH_LOCAL.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                final HashMap<Integer,String> data = (HashMap<Integer, String>) intent.getSerializableExtra(EXTRA_LOCAL_DATA);
                handleFromLocal(url, data);
            }
        }
    }

    private void handleFromLocal(final String url, final HashMap<Integer,String> data) {
        HashMap.Entry<Integer,String> entry = obtainEntry(data);
        if (entry != null) {
            String completionUrl = url+"?"+entry.getValue();//完整的url
            Log.d("request", completionUrl);
            Request<String> request = new Request<>();
            ResponseReader<String> read = new StringResponseReader();
            Response<String> response = request.performGet(completionUrl,read);
            if (response.isSuccess()){
                data.remove(entry.getKey());
                Log.d( "response: ",response.get());
                PersistenceService.getInstance(this).delete(entry.getKey());

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handleFromLocal(url, data);//递归

            }else {
                //失败保存到本地
                Log.d( "error: ",response.getNetworkResponse().getResponseMessage());
                //handleFromLocal(url, data);//递归
            }
        }else {
            PersistenceService.getInstance(this).end();//上传完
        }
    }

    private void delay(final DelayCallback callback, long delayMs) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.callback();
            }
        }, delayMs);
    }

    interface DelayCallback{
        void callback();
    }

    private HashMap.Entry<Integer,String> obtainEntry(HashMap<Integer, String> data) {
        for (HashMap.Entry<Integer,String> entry :data.entrySet()){
            return entry;
        }
        return null;
    }

}
