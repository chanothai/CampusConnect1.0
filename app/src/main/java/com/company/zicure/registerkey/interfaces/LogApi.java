package com.company.zicure.registerkey.interfaces;

import java.util.Map;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.ResponseUserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by BallOmO on 10/11/2016 AD.
 */
public interface LogApi {
    @POST("Api/secure/login.json")
    Call<BaseResponse> responseLogin(@Body DataModel dataModel);

    @POST("Api/secure/registerUser.json")
    Call<BaseResponse> callRegister(@Body DataModel dataModel);

    @POST("Api/secure/checkOTP.json")
    Call<BaseResponse> validateOTP(@Body DataModel dataModel);

    @GET("Api/version.json")
    Call<BaseResponse> checkVersion();

    @POST("Api/secure/getOauthTokenFromUserToken.json")
    Call<BaseResponse> requestAuthenToken(@Body DataModel dataModel);

    @GET("Api/getUserInfo.json")
    Call<ResponseUserInfo> requestUserInfo(@QueryMap Map<String, String> authToken);

    @POST("Api/secure/approveDevice.json")
    Call<BaseResponse> approveDevice(@Body DataModel dataModel);
}
