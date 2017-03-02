package com.company.zicure.survey.interfaces;

import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.PostAnswer;
import com.company.zicure.survey.models.PostQuestionnaire;
import com.company.zicure.survey.models.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public interface LogApi {
    @GET("API2/startTransaction.json")
    Call<QuestionResponse> getQuestion();

    @POST("API2/answerTransaction.json")
    Call<AnswerResponse> postAnswer(@Body PostAnswer postAnswer);

    @GET("API2/endTransaction.json")
    Call<AnswerResponse> submitAnswer();
}
