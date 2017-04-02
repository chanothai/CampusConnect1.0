package com.company.zicure.registerkey;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.otp.OTPRequest;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;

public class OTPActivity extends BaseActivity implements EditText.OnEditorActionListener{

    @Bind(R.id.edit_otp)
    EditText editOTP;
    @Bind(R.id.btn_otp)
    Button btnOTP;

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);

        editOTP.setOnEditorActionListener(this);
        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            username = bundle.getString("username");
        }
    }

    @OnClick(R.id.btn_otp)
    public void onClick(){
        checkInput();
    }

    private void checkInput(){
        String strOTP = editOTP.getText().toString().trim();
        if (strOTP.length() == 4){
            OTPRequest otpRequest = new OTPRequest();
            OTPRequest.User result = new OTPRequest.User();
            result.setUsername(username);
            result.setOtp(strOTP);
            otpRequest.setUser(result);

            String str = new Gson().toJson(otpRequest);
            Log.d("OTPRequest", str);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(otpRequest));
            Log.d("OTPModel", resultEncrypt);

            DataModel otpModel = new DataModel();
            otpModel.setData(resultEncrypt);

            str = new Gson().toJson(otpModel);
            Log.d("OTPRequest", str);

            showLoadingDialog();
            ClientHttp.getInstance(this).validateOTP(otpModel);
        }else{
            Toast.makeText(getApplicationContext(), R.string.content_alert_OTP, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEventCheckOTP(BaseResponse baseResponse){
        String str = new Gson().toJson(baseResponse);
        Log.d("BaseResponse", str);
        BaseResponse.Result result = baseResponse.getResult();
        if (!result.getSuccess().isEmpty()) {
            String[] arrStr = result.geteResult().split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("EncryptCart", "DecryptData: " + decrypt);

            if (decrypt != null){
                decodeJson(decrypt);
            }
        }else {
            Toast.makeText(getApplicationContext(), result.getError(), Toast.LENGTH_SHORT).show();
        }
        dismissDialog();
    }

    private void decodeJson(String decrypt){
        try{
            JSONObject jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (!success.isEmpty()){
                Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
                openActivity(LoginActivity.class, true);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }else{
                String error = jsonObject.getString("Error");
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        if (actionID == EditorInfo.IME_ACTION_DONE){
            checkInput();
        }
        return false;
    }
}
