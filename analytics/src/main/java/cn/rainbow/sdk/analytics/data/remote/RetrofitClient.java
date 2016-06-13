package cn.rainbow.sdk.analytics.data.remote;

import cn.rainbow.sdk.analytics.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bvin on 2016/4/18.
 */
public class RetrofitClient {

    private static final boolean ENABLE_ENCODE = true;

    /**
     * OkHttpClient配置请求拦截器
     * @return
     */
    private static OkHttpClient configClient() {
        OkHttpClient.Builder requestBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            //log interceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            requestBuilder.addInterceptor(loggingInterceptor);
        }

        return requestBuilder.build();
    }

    public static Retrofit getInstance() {
        return create(Api.HOST);
    }

    public static Retrofit getInstance(String baseUrl) {
        return create(baseUrl);
    }

    private static Retrofit create(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private RetrofitClient() {
    }
}
