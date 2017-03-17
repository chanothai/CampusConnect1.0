package com.company.zicure.registerkey.interfaces;

import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.LoginModel;
import com.company.zicure.registerkey.models.UserRequest;
import com.company.zicure.registerkey.models.otp.OTPModel;
import com.company.zicure.registerkey.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by BallOmO on 10/11/2016 AD.
 */
public interface LogApi {
    @POST("Api/login.json")
    Call<BaseResponse> responseLogin(@Body LoginModel loginModel);

    @POST("Api/registerUser.json")
    Call<BaseResponse> callRegister(@Body UserModel userRegister);

    @POST("Api/checkOTP.json")
    Call<BaseResponse> validateOTP(@Body OTPModel otpModel);

    @POST("")
    Call<BaseResponse> callUserDetail(@Body UserRequest userRequest);
}
