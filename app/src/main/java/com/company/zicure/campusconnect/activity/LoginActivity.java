package com.company.zicure.campusconnect.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.network.ClientHttp;
import com.company.zicure.campusconnect.security.EncryptionAES;
import com.google.gson.Gson;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.login.LoginRequest;
import gallery.zicure.company.com.modellibrary.models.login.LoginResponse;
import gallery.zicure.company.com.modellibrary.models.register.ResponseUniversities;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class LoginActivity extends BaseActivity implements View.OnKeyListener, EditText.OnEditorActionListener{
    /** Make: View **/
    @Bind(R.id.username)
    EditText editUser;
    @Bind(R.id.password)
    EditText editPass;
    @Bind(R.id.btnConnect)
    Button btnLogin;
    @Bind(R.id.txt_link)
    TextView txtLink;
    @Bind(R.id.label_forgot_password)
    TextView forgotPass;

    /** Make: Properties **/
    private int orgID;
    private List<String> universities = null;
    private List<ResponseUniversities.ResultOrg.DataOrg> listORG = null;

    private String strUser = "", strPass = "";
    private byte[] keyByte = null;
    private FingerPrintAuthHelper mFingerPrintAuthHelper = null;
    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
        editUser.setOnKeyListener(this);
        setTextClick();

        if (savedInstanceState == null){
            sharedPref = getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
            keyByte = Base64.decode(VariableConnect.staticKey.getBytes(), Base64.NO_WRAP);

//            FingerprintHandler handler = new FingerprintHandler(this);
//            mFingerPrintAuthHelper = handler.initFingerprint();
//            mFingerPrintAuthHelper.startAuth();
        }
    }

    /** OnClick **/
    @OnClick(R.id.btnConnect)
    public void setBtnLogin(){
        checkInput();
    }

    private void checkInput(){
        strUser = editUser.getText().toString().trim();
        strPass = editPass.getText().toString().trim();

        if (!strUser.isEmpty() && !strPass.isEmpty()){
            LoginRequest loginRequest = new LoginRequest();
            LoginRequest.User result = new LoginRequest.User();
            result.setOrgID(Integer.toString(orgID));
            result.setUsername(strUser);
            result.setPassword(strPass);
            loginRequest.setUser(result);

            showLoadingDialog();
            ClientHttp.getInstance(this).login(loginRequest);
        }
    }

//    private DataModel setLoginModel(String strUser, String strPass){
//        LoginRequest loginRequest = new LoginRequest();
//        LoginRequest.User result = new LoginRequest.User();
//
//        this.strUser = strUser;
//        result.setUsername(strUser);
//        result.setPassword(strPass);
//        loginRequest.setUser(result);
//
//        String str = new Gson().toJson(loginRequest);
//        Log.d("LoginRequest",  str);
//
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String resultEncrypt = null;
//        resultEncrypt = EncryptionAES.newInstance(keyByte).encrypt(gson.toJson(loginRequest));
//        Log.d("LoginModel", resultEncrypt);
//
//        final DataModel dataModel = new DataModel();
//        dataModel.setData(resultEncrypt);
//
//        return dataModel;
//    }

    @Subscribe
    public void onEventLogin(LoginResponse response) {
        String str = new Gson().toJson(response);
        Log.d("LoginResponse", str);
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            String token = response.getResult().getData().getUser().getToken();
            String dynamicKey = response.getResult().getData().getUser().getDynamicKey();
            store(token, dynamicKey);

            String[] strArr = {token, strUser, dynamicKey};
            Bundle bundle = setBundle(strArr);

            openActivity(CheckLoginActivity.class, bundle, true);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }else{
            Toast.makeText(this, response.getResult().getError(), Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }

    @Subscribe
    public void onEventLoginSecure(BaseResponse baseResponse){
        String str = new Gson().toJson(baseResponse);
        Log.d("BaseResponse", str);
        BaseResponse.Result result = baseResponse.getResult();
        if (!result.getSuccess().isEmpty()){
            String[] arrStr = result.geteResult().split(getString(R.string.key_iv));
            String decryptStr = EncryptionAES.newInstance(keyByte).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("Decrypt", "DecryptDynamicKey: " + decryptStr);

            if (decryptStr != null){
                decodeJson(decryptStr);
            }
        }else{
            Toast.makeText(getApplicationContext(), result.getError(), Toast.LENGTH_LONG).show();
            dismissDialog();
        }
    }

    private void decodeJson(String decryptStr){
        try{
            JSONObject jsonObject = new JSONObject(decryptStr);

            String success = jsonObject.getString("Success");
            if (!success.isEmpty()){
                jsonObject = jsonObject.getJSONObject("Data");
                jsonObject = jsonObject.getJSONObject("User");

                String token = jsonObject.getString("token");
                String dynamicKey = jsonObject.getString("dynamic_key");

                store(token, dynamicKey);
                String[] strArr = {token, strUser,dynamicKey};
                Bundle bundle = setBundle(strArr);

                openActivity(CheckLoginActivity.class,bundle ,true);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }else{
                String error = jsonObject.getString("Error");
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        dismissDialog();
    }

    private Bundle setBundle(String[] strArr){
        Bundle bundle = new Bundle();
        bundle.putStringArray(getString(R.string.user_secret), strArr);
        return bundle;
    }

    private void store(String token, String dynamicKey){
        byte[] key = Base64.decode(dynamicKey.getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(key);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token_login), token);
        editor.putString(getString(R.string.dynamic_key), dynamicKey);
        editor.putString(getString(R.string.username), strUser);
        editor.apply();
    }


    public void setTextClick(){
        String strAll= getString(R.string.text_link_th);
        String strLinkSignUP = getString(R.string.text_link_th);
        int start = strAll.indexOf(strLinkSignUP);
        int end = start + strLinkSignUP.length();

        SpannableString spannableString = new SpannableString(strAll);
        spannableString.setSpan(new CallLink(), start, end,0);

        txtLink.setText(spannableString);
        txtLink.setMovementMethod(new LinkMovementMethod());

        String startTxt = getString(R.string.text_forgot_password_th);
        String endTxt = getString(R.string.text_forgot_password_th);
        int start2 = startTxt.indexOf(endTxt);
        int end2 = start + endTxt.length();

        SpannableString spannableString1 = new SpannableString(startTxt);
        spannableString1.setSpan(new CallLink(), start2, end2, 0);

        forgotPass.setText(spannableString1);
        forgotPass.setMovementMethod(new LinkMovementMethod());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //prevent enter for editText
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        if (actionID == EditorInfo.IME_ACTION_DONE){
            checkInput();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mFingerPrintAuthHelper.stopAuth();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    private class CallLink extends ClickableSpan{
        @Override
        public void onClick(View widget) {
            TextView message = (TextView) widget;
            Spanned spanned = (Spanned) message.getText();
            int start = spanned.getSpanStart(this);
            int end = spanned.getSpanEnd(this);

            String checkLink = spanned.subSequence(start,end).toString();

            if (checkLink.equalsIgnoreCase(getString(R.string.text_link_th))){
                openActivity(RegisterActivity.class, false);
            }else if (checkLink.equalsIgnoreCase(getString(R.string.text_forgot_password_th))){
                openActivity(ForgotPasswordActivity.class, false);
            }
        }
    }
}
