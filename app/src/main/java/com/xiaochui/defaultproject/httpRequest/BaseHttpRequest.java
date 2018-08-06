package com.xiaochui.defaultproject.httpRequest;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android-Pc on 2017/4/10.
 */

public class BaseHttpRequest {
    private static BaseHttpRequest baseHttpRequest;
    private static OkHttpClient okHttpClient;
    private ApiService apiService;

    public static BaseHttpRequest getInstance() {
        if (baseHttpRequest == null) {
            synchronized (BaseHttpRequest.class) {
                if (baseHttpRequest == null) {
                    baseHttpRequest = new BaseHttpRequest();
                }
            }
        }
        InitOkHttpClient();
        return baseHttpRequest;
    }

    /**
     * init okHttpClient
     */
    private static void InitOkHttpClient() {
        if (okHttpClient == null) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            if (AppBuildConfig.DEBUG) {
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            }
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new LoggingInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
    }

    public ApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ApiService.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public void clearRetrofit() {
        apiService = null;
    }
}
