package com.company.zicure.registerkey.network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

/**
 * Created by 4GRYZ52 on 11/26/2016.
 */

public class RetrofitAPI {
    private static RetrofitAPI me;
    private Retrofit retrofit = null;
    private String url = null;

    public RetrofitAPI(String url){
        this.url = url;
    }

    public static RetrofitAPI newInstance(String url){
        if (me == null){
            me = new RetrofitAPI(url);
        }
        return me;
    }

    public Retrofit getRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (retrofit != null){
            return retrofit;
        }
        return null;
    }
}
