package cn.rainbow.sdk.analytics.net;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.IllegalFormatCodePointException;
import java.util.Map;

import cn.rainbow.sdk.analytics.data.local.DBProvider;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.net.response.Response;
import cn.rainbow.sdk.analytics.net.response.reader.StringResponseReader;
import cn.rainbow.sdk.analytics.utils.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class RequestService extends IntentService {
    private static final String TAG = "THAnalytics";
    private static final String URL = "URL";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String ROW_ID = "ROW_ID";

    /**
     *
     * @param url URL
     * @param tableName 表名
     * @param rowId 记录id
     */
    public static void start(Context context, String url, String tableName, int rowId) {
        Intent starter = new Intent(context, RequestService.class);
        starter.putExtra(URL,url);
        starter.putExtra(TABLE_NAME,tableName);
        starter.putExtra(ROW_ID,rowId);
        context.startService(starter);
    }

    private StringResponseReader mResponseReader;
    private Gson mGson;

    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra(URL);
            String tableName = intent.getStringExtra(TABLE_NAME);
            int rowId = intent.getIntExtra(ROW_ID, -1);
            if (!TextUtils.isEmpty(url)) {
                Request<String> request = new Request<>();
                if (mResponseReader == null) {
                    mResponseReader = new StringResponseReader();
                }
                Log.e(TAG + "-request", url);
                Response<String> response = request.performGet(url, mResponseReader);
                if (response!=null&&response.isSuccess() && !TextUtils.isEmpty(response.get())) {
                    Log.e(TAG + "-response", response.get());
                    if (mGson == null) {
                        mGson = new Gson();
                    }
                    try {
                        Model model = mGson.fromJson(response.get(), Model.class);
                        if (model != null) {
                            if (model.isOK()) {
                                if (!TextUtils.isEmpty(tableName) && rowId > -1) {
                                    Uri tableUri = Uri.parse("content://" + DBProvider.AUTHORITY + "/" + tableName);
                                    int rowNumber = getContentResolver().delete(ContentUris.withAppendedId(tableUri, rowId), null, null);
                                    Log.d("delete:", tableUri.toString() + tableName + "/" + rowNumber);
                                }
                            }
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


}
