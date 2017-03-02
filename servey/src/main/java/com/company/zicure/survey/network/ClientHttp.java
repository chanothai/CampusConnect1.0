package com.company.zicure.survey.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.survey.R;
import com.company.zicure.survey.interfaces.LogApi;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.PostAnswer;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.utilize.EventBusCart;
import com.company.zicure.survey.utilize.GsonCart;
import com.company.zicure.survey.utilize.ModelCart;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public class ClientHttp {
    private Context context = null;
    private static ClientHttp clientHttp;

    public ClientHttp(Context context){
        this.context = context;
    }

    public static ClientHttp newInstance(Context context){
        if (clientHttp == null){
            clientHttp = new ClientHttp(context);
        }
        return clientHttp;
    }

    private LogApi getService(){
        Retrofit retrofit = RetrofitAPI.newInstance(context.getString(R.string.url_question)).getRetrofit();
        LogApi service = retrofit.create(LogApi.class);
        return service;
    }

    public void requestQuestion(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging); //<-- This is important line!

        Call<QuestionResponse> callQuestion = getService().getQuestion();
        callQuestion.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.body() != null){
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(response.body());
                    Log.d("QuestionResponse" , jsonStr);
                    EventBusCart.getInstance().getEventBus().post(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                QuestionResponse.Result result = new QuestionResponse().new Result();
                result.setSuccess("");
                result.setError("data failed");


                EventBusCart.getInstance().getEventBus().post(result);

                t.printStackTrace();
            }
        });
    }

    public void postAnswerData(PostAnswer postAnswer){
        Call<AnswerResponse> callAnswer = getService().postAnswer(postAnswer);
        callAnswer.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if (response.body() != null){
                    Log.d("AnswerResponse", new GsonCart<AnswerResponse>(response.body()).getJsonStr());
                    EventBusCart.getInstance().getEventBus().post(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void submitAnswerData(){
        Call<AnswerResponse> callAnswer = getService().submitAnswer();
        callAnswer.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if (response.body() != null){
                    Log.d("AnswerResponse", new GsonCart<AnswerResponse>(response.body()).getJsonStr());
                    EventBusCart.getInstance().getEventBus().post(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
