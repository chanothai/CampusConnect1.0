package com.company.zicure.registerkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.LoginModel;
import com.company.zicure.registerkey.models.login.LoginRequest;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.company.zicure.registerkey.utilize.NextzyUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnKeyListener,TextWatcher, EditText.OnEditorActionListener{
    @Bind(R.id.username)
    EditText editUser;
    @Bind(R.id.password)
    EditText editPass;
    @Bind(R.id.btnConnect)
    Button btnLogin;
    @Bind(R.id.txt_link)
    TextView txtLink;

    private String strUser = "", strPass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
        editUser.setOnKeyListener(this);
        editUser.addTextChangedListener(this);
        setTextClick();

        if (savedInstanceState == null){
            if (restore()){
                openActivity(MainMenuActivity.class, true);
            }else{
                byte[] keyByte = Base64.decode(getString(R.string.staticKey).getBytes(), Base64.NO_WRAP);
                ModelCart.getInstance().getKeyModel().setKey(keyByte);
            }


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.btnConnect)
    public void setBtnLogin(){
        checkInput();
    }

    private void checkInput(){
//        strUser = editUser.getText().toString().trim();
//        strPass = editPass.getText().toString().trim();
        strUser = "082-445-6225";
        strPass = "082-445-6225";

        if (strUser.length() == 12 && !strPass.isEmpty()){
            LoginRequest loginRequest = new LoginRequest();
            LoginRequest.Result result = loginRequest.new Result();
            result.setUsername(strUser);
            result.setPassword(strPass);

            loginRequest.setResult(result);

            String str = new Gson().toJson(loginRequest);
            Log.d("LoginRequest",  str);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(loginRequest));
            Log.d("LoginModel", resultEncrypt);

            final LoginModel loginModel = new LoginModel();
            loginModel.setLogin(resultEncrypt);
            str = new Gson().toJson(loginModel);
            Log.d("LoginRequest",  str);

            showLoadingDialog();
            ClientHttp.getInstance(getApplicationContext()).login(loginModel);
        }else{
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
            if (editUser.getText().toString().trim().length() < 12){
                editUser.requestFocus();
            }
            else if (editPass.getText().toString().trim().isEmpty()){
                editPass.requestFocus();
            }
        }
    }

    private void store(String token, String dynamicKey){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token_login), token);
        editor.putString(getString(R.string.dynamic_key), dynamicKey);
        editor.commit();
    }

    private boolean restore(){
        SharedPreferences pref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = pref.getString(getString(R.string.token_login), null);
        String phoneUser = pref.getString(getString(R.string.phone_key), null);

        if (phoneUser == null){
            openActivity(RegisterActivity.class, true);
        }else{
            if (token != null){
                return true;
            }
        }
        return false;
    }

    @Subscribe
    public void onEvent(BaseResponse baseResponse){
        String str = new Gson().toJson(baseResponse);
        Log.d("BaseResponse", str);
        BaseResponse.Result result = baseResponse.getResult();
        if (!result.getSuccess().isEmpty()){
            String[] arrStr = result.getSuccess().split(getString(R.string.key_iv));
            String decryptStr = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("Decrypt", "DecryptDynamicKey: " + decryptStr);
            try{
                JSONObject jsonObject = new JSONObject(decryptStr);
                String token = jsonObject.getString("token");
                String dynamicKey = jsonObject.getString("dynamic_key");

                String[] arrKey = dynamicKey.split(getString(R.string.key_iv));
                String decryptKey = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrKey[0], arrKey[1].getBytes());//(text, key


                byte[] secretKey = Base64.decode(decryptKey.getBytes(), Base64.NO_WRAP);

                if (decryptKey != null){
                    //store data for first login
                    store(token, new String(secretKey));
                    openActivity(MainMenuActivity.class, true);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                    Toast.makeText(getApplicationContext(), "Login: " + token, Toast.LENGTH_LONG).show();
                    dismissDialog();

                }else{
                    Toast.makeText(getApplicationContext(), "Decrypt Error", Toast.LENGTH_LONG).show();
                    dismissDialog();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), result.getError(), Toast.LENGTH_LONG).show();
            dismissDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }


    public void setTextClick(){
        String strAll= getString(R.string.detail_link);
        String strLinkSignUP = getString(R.string.signup_link);
        int start = strAll.indexOf(strLinkSignUP) + 1;
        int end = start + strLinkSignUP.length();

        String strLinkForgotPass = getString(R.string.forgot_password_link);
        int start2 = strAll.indexOf(strLinkForgotPass);
        int end2 = start2 + strLinkForgotPass.length();

        SpannableString spannableString = new SpannableString(strAll);
        spannableString.setSpan(new CallLink(), start, end,0);
        spannableString.setSpan(new CallLink(), start2, end2, 0);

        txtLink.setText(spannableString);
        txtLink.setMovementMethod(new LinkMovementMethod());
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {
        String txtResult =  "";
        try {
            if (position == 2){
                txtResult = editUser.getText().toString().trim() + "-";
                editUser.setText(txtResult);
                editUser.setSelection(4);
            }
            else if (position == 6){
                txtResult = editUser.getText().toString().trim() + "-";
                editUser.setText(txtResult);
                editUser.setSelection(8);
            }
        }catch (Exception e){
            editUser.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        if (actionID == EditorInfo.IME_ACTION_DONE){
            checkInput();
        }
        return false;
    }

    private class CallLink extends ClickableSpan{
        @Override
        public void onClick(View widget) {
            TextView message = (TextView) widget;
            Spanned spanned = (Spanned) message.getText();
            int start = spanned.getSpanStart(this);
            int end = spanned.getSpanEnd(this);

            String checkLink = spanned.subSequence(start,end).toString();

            if (checkLink.equalsIgnoreCase(getString(R.string.signup_link))){
                Toast.makeText(LoginActivity.this, "Yeah!1", Toast.LENGTH_SHORT).show();
            }else if (checkLink.equalsIgnoreCase(getString(R.string.forgot_password_link))){
                Toast.makeText(LoginActivity.this, "Yeah!2", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
