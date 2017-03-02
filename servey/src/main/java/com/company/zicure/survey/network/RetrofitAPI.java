package com.company.zicure.survey.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (retrofit != null){
            return retrofit;
        }
        return null;
    }
}
