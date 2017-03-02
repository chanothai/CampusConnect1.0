package com.company.zicure.registerkey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.otp.OTPModel;
import com.company.zicure.registerkey.models.otp.OTPRequest;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPActivity extends BaseActivity implements EditText.OnEditorActionListener{

    @Bind(R.id.edit_otp)
    EditText editOTP;
    @Bind(R.id.btn_otp)
    Button btnOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);

        editOTP.setOnEditorActionListener(this);
        if (savedInstanceState == null){

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
            OTPRequest.Result result = otpRequest.new Result();
            result.setOtp(strOTP);
            otpRequest.setResult(result);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(otpRequest));
            Log.d("OTPModel", resultEncrypt);

            OTPModel otpModel = new OTPModel();
            otpModel.setEncryptOTP(resultEncrypt);

            showLoadingDialog();
            ClientHttp.getInstance(this).validateOTP(otpModel);
        }else{
            Toast.makeText(getApplicationContext(), R.string.content_alert_OTP, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEvent(BaseResponse baseResponse){
        BaseResponse.Result result = baseResponse.getResult();
        if (!result.getSuccess().isEmpty()) {
            String[] arrStr = result.getSuccess().split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("EncryptCart", "DecryptData: " + decrypt);
            if (!decrypt.isEmpty()) {
                Toast.makeText(this, decrypt, Toast.LENGTH_SHORT).show();
                openActivity(LoginActivity.class, true);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }

            dismissDialog();
        }else {
            Toast.makeText(getApplicationContext(), result.getError(), Toast.LENGTH_SHORT).show();
            dismissDialog();
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
