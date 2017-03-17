package com.company.zicure.registerkey.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.interfaces.LogApi;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.LoginModel;
import com.company.zicure.registerkey.models.UserRequest;
import com.company.zicure.registerkey.models.otp.OTPModel;
import com.company.zicure.registerkey.models.UserModel;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by BallOmO on 10/13/2016 AD.
 */
public class ClientHttp {
    private Context context = null;
    private static ClientHttp me;

    private final String LOGAPI = "API_RESPONSE";
    private String jsonStr;

    private Retrofit retrofit = null;
    private LogApi service = null;

    public ClientHttp(Context context){
        this.context = context;
        retrofit = RetrofitAPI.newInstance(context.getString(R.string.url_identity_server)).getRetrofit();
        service = retrofit.create(LogApi.class);
    }

    public static ClientHttp getInstance(Context context){
        if (me == null){
            me = new ClientHttp(context);
        }
        return me;
    }

    public void register(UserModel user){
        Call<BaseResponse> callRegis = service.callRegister(user);
        callRegis.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("RegisterResponse", new Gson().toJson(response.body()));
                if (response.body() != null){
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }else{
                    BaseResponse baseResponse = new BaseResponse();
                    BaseResponse.Result result = new BaseResponse().new Result();
                    result.setError("Data null");
                    result.setSuccess("");
                    baseResponse.setResult(result);

                    EventBusCart.getInstance().getEventBus().post(baseResponse);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                BaseResponse baseResponse = new BaseResponse();
                BaseResponse.Result result = new BaseResponse().new Result();
                result.setError("Connect Error");
                result.setSuccess("");
                baseResponse.setResult(result);

                EventBusCart.getInstance().getEventBus().post(baseResponse);
            }
        });
    }

    public void login(LoginModel model){
        Log.d("LoginModel", new Gson().toJson(model));
        Call<BaseResponse> loginResponseCall = service.responseLogin(model);
        loginResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("LoginResponse", new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    setResponse("Result: null");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                setResponse("Connect or Model Error");
            }
        });
    }

    public void validateOTP(OTPModel model){
        Log.d("OTPModel", new Gson().toJson(model));
        Call<BaseResponse> callOTP = service.validateOTP(model);
        callOTP.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    setResponse("Result: null");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                setResponse("Connect Error");
            }
        });
    }

    private void requestUserDetail(UserRequest userRequest){
        Call<BaseResponse> callUserDetail = service.callUserDetail(userRequest);
        callUserDetail.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setResponse(String message){
        BaseResponse baseResponse = new BaseResponse();
        BaseResponse.Result result = baseResponse.new Result();
        result.setSuccess("");
        result.setError(message);
        baseResponse.setResult(result);

        EventBusCart.getInstance().getEventBus().post(baseResponse);
    }
}
