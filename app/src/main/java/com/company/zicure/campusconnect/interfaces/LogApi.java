package com.company.zicure.campusconnect.interfaces;

import java.util.Map;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;
import gallery.zicure.company.com.modellibrary.models.contact.RequestAddContact;
import gallery.zicure.company.com.modellibrary.models.contact.ResponseAddContact;
import gallery.zicure.company.com.modellibrary.models.contact.ResponseContactList;
import gallery.zicure.company.com.modellibrary.models.login.LoginRequest;
import gallery.zicure.company.com.modellibrary.models.login.LoginResponse;
import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard;
import gallery.zicure.company.com.modellibrary.models.quiz.ResponseQuiz;
import gallery.zicure.company.com.modellibrary.models.register.RegisterRequest;
import gallery.zicure.company.com.modellibrary.models.register.ResponseRegister;
import gallery.zicure.company.com.modellibrary.models.register.ResponseUniversities;
import gallery.zicure.company.com.modellibrary.models.register.VerifyRequest;
import gallery.zicure.company.com.modellibrary.models.register.VerifyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by BallOmO on 10/11/2016 AD.
 */
public interface LogApi {
    @GET("Api/version.json")
    Call<BaseResponse> checkVersion();

    @POST("Api/secure/login.json")
    Call<BaseResponse> responseLogin(@Body DataModel dataModel);

    @POST("Api/login.json")
    Call<LoginResponse> callLogin(@Body LoginRequest loginRequest);

    @GET("Api/callOrgsList.json")
    Call<ResponseUniversities> callORG();

    @POST("Api/secure/registerUser.json")
    Call<BaseResponse> callRegisterSecure(@Body DataModel dataModel);

    @POST("Api/registerUser.json")
    Call<ResponseRegister> callRegister(@Body RegisterRequest registerRequest);

    @POST("Api/verifyUser.json")
    Call<VerifyResponse> verifyUser(@Body VerifyRequest verifyRequest);

    @POST("Api/secure/getOauthTokenFromUserToken.json")
    Call<BaseResponse> requestAuthenToken(@Body DataModel dataModel);

    @POST("Api/secure/approveDevice.json")
    Call<BaseResponse> approveDevice(@Body DataModel dataModel);

    @GET("Api/userAccessControl.json")
    Call<ResponseBlocUser> requestUserBloc(@QueryMap Map<String, String> category);

    @GET("Api/profileData.json")
    Call<ResponseIDCard> requestProfile(@QueryMap Map<String, String> token);

    @GET("Api/showContactList.json")
    Call<ResponseContactList> requestContact(@QueryMap Map<String, String> token);

    @GET("Api/checkTakeQuiz.json")
    Call<ResponseQuiz> requestQuiz(@QueryMap Map<String, String> token);

    @POST("Api/addContact.json")
    Call<ResponseAddContact> requestAddContact(@Body RequestAddContact addContact);
}
