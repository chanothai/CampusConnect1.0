package com.company.zicure.registerkey.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.registerkey.interfaces.LogApi;
import gallery.zicure.company.com.modellibrary.models.CategoryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.ResponseUserInfo;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
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
    private Gson gson = null;

    public ClientHttp(Context context){
        this.context = context;
        retrofit = RetrofitAPI.newInstance(VariableConnect.urlIdentityServer).getRetrofit();
        service = retrofit.create(LogApi.class);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static ClientHttp getInstance(Context context){
        if (me == null){
            me = new ClientHttp(context);
        }
        return me;
    }

    public void register(DataModel user){
        Call<BaseResponse> callRegis = service.callRegister(user);
        callRegis.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("RegisterResponse",gson.toJson(response.body()));
                if (response.body() != null){
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }else{
                    BaseResponse baseResponse = new BaseResponse();
                    BaseResponse.Result result = new BaseResponse.Result();
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
                BaseResponse.Result result = new BaseResponse.Result();
                result.setError("Connect Error");
                result.setSuccess("");
                baseResponse.setResult(result);

                EventBusCart.getInstance().getEventBus().post(baseResponse);
            }
        });
    }

    public void login(DataModel model){
        Log.d("LoginModel", gson.toJson(model));
        Call<BaseResponse> loginResponseCall = service.responseLogin(model);
        loginResponseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("LoginResponse", new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    ResponseError.setResponse("Result: null");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                ResponseError.setResponse("Connect or Model Error");
            }
        });
    }

    public void validateOTP(DataModel model){
        Log.d("OTPModel", gson.toJson(model));
        Call<BaseResponse> callOTP = service.validateOTP(model);
        callOTP.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    ResponseError.setResponse("Result: null");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                ResponseError.setResponse("Connect Error");
            }
        });
    }

    public void checkVersionApp(){
        Call<BaseResponse> validateVersion = service.checkVersion();
        validateVersion.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
                    if (response.body() != null){
                        EventBusCart.getInstance().getEventBus().post(response.body());
                    }
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

    public void requestAuthToken(DataModel dataModel){
        Log.d("DataModel", gson.toJson(dataModel));
        Call<BaseResponse> authToken = service.requestAuthenToken(dataModel);
        authToken.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestUserInfo(String path){
        Log.d("Path", path);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("authToken", path);
        Call<ResponseUserInfo> userInfo = service.requestUserInfo(map);
        userInfo.enqueue(new Callback<ResponseUserInfo>() {
            @Override
            public void onResponse(Call<ResponseUserInfo> call, Response<ResponseUserInfo> response) {
                Log.d("UserInfo", gson.toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                    ResponseError.setUserInfoResponseError("Response_data: null");
                }
            }

            @Override
            public void onFailure(Call<ResponseUserInfo> call, Throwable t) {
                t.printStackTrace();
                ResponseError.setUserInfoResponseError("Time out");
            }
        });
    }

    public void requestUserBloc(String authToken) {
        HashMap<String, String> map = new HashMap<>();
        map.put("authToken", authToken);
        Call<CategoryModel> userBloc = service.requestUserBloc(map);
        userBloc.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void approveDevice(DataModel dataModel){
        Call<BaseResponse> approve = service.approveDevice(dataModel);
        approve.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
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


    public static class ResponseError {

        public static void setUserInfoResponseError(String message){
            ResponseUserInfo response = new ResponseUserInfo();
            ResponseUserInfo.ResultUserInfo result = new ResponseUserInfo.ResultUserInfo();
            result.setSuccess("");
            result.setError(message);
            response.setResult(result);

            EventBusCart.getInstance().getEventBus().post(response);
        }

        public static void setResponse(String message){
            BaseResponse baseResponse = new BaseResponse();
            BaseResponse.Result result = new BaseResponse.Result();
            result.setSuccess("");
            result.setError(message);
            baseResponse.setResult(result);

            EventBusCart.getInstance().getEventBus().post(baseResponse);
        }
    }
}
