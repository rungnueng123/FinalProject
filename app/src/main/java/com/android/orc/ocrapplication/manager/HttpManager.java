package com.android.orc.ocrapplication.manager;

import com.android.orc.ocrapplication.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class HttpManager {

//    private static final String BASE_URL = "http://128.199.154.30:3000/";

    private static HttpManager instances;

    public static HttpManager getInstance() {
        if (instances == null)
            instances = new HttpManager();
        return instances;
    }

    private ApiService service;



    private HttpManager() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://167.99.76.64:3000/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(ApiService.class);
        }
    }

    public ApiService getService() {
        return service;
    }
}
