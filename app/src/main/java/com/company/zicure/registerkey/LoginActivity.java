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

import com.company.zicure.registerkey.activity.CheckLoginActivity;
import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.login.LoginRequest;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

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
    private String restoreUser = null, restorePass = null;

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
            byte[] keyByte = Base64.decode(VariableConnect.staticKey.getBytes(), Base64.NO_WRAP);
            ModelCart.getInstance().getKeyModel().setKey(keyByte);
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
        strUser = editUser.getText().toString().trim();
        strPass = editPass.getText().toString().trim();

        if (strUser.length() == 12 && !strPass.isEmpty()){
            DataModel dataModel = setLoginModel(strUser, strPass, false);
            String str = new Gson().toJson(dataModel);
            Log.d("LoginRequest",  str);
            showLoadingDialog();
            ClientHttp.getInstance(getApplicationContext()).login(dataModel);
        }
    }

    private DataModel setLoginModel(String strUser, String strPass, boolean restore){
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest.User result = new LoginRequest.User();

        if (!restore){
            String[] user = strUser.split("-");
            strUser = user[0]+ user[1] + user[2];
        }

        this.strUser = strUser;
        result.setUsername(strUser);
        result.setPassword(strPass);
        loginRequest.setUser(result);

        String str = new Gson().toJson(loginRequest);
        Log.d("LoginRequest",  str);

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String resultEncrypt = null;
        resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(loginRequest));
        Log.d("LoginModel", resultEncrypt);

        final DataModel dataModel = new DataModel();
        dataModel.setData(resultEncrypt);

        return dataModel;
    }

    @Subscribe
    public void onEventLogin(BaseResponse baseResponse){
        String str = new Gson().toJson(baseResponse);
        Log.d("BaseResponse", str);
        BaseResponse.Result result = baseResponse.getResult();
        if (!result.getSuccess().isEmpty()){
            String[] arrStr = result.geteResult().split(getString(R.string.key_iv));
            String decryptStr = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("Decrypt", "DecryptDynamicKey: " + decryptStr);

            if (decryptStr != null){
                decodeJson(decryptStr);
            }
        }else{
            Toast.makeText(getApplicationContext(), result.getError(), Toast.LENGTH_LONG).show();
            dismissDialog();
        }
    }

    private Bundle setBundle(String[] strArr){
        Bundle bundle = new Bundle();
        bundle.putStringArray(getString(R.string.user_secret), strArr);
        return bundle;
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
                String[] strArr = {token, strUser};
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


    private void store(String token, String dynamicKey){
        byte[] key = Base64.decode(dynamicKey.getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(key);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token_login), token);
        editor.putString(getString(R.string.dynamic_key), dynamicKey);
        editor.putString(getString(R.string.username), strUser);
        editor.commit();
    }


    public void setTextClick(){
        String strAll= getString(R.string.detail_link);
        String strLinkSignUP = getString(R.string.signup_link);
        int start = strAll.indexOf(strLinkSignUP);
        int end = start + strLinkSignUP.length();

//        String strLinkForgotPass = getString(R.string.forgot_password_link);
//        int start2 = strAll.indexOf(strLinkForgotPass);
//        int end2 = start2 + strLinkForgotPass.length();

        SpannableString spannableString = new SpannableString(strAll);
        spannableString.setSpan(new CallLink(), start, end,0);
//        spannableString.setSpan(new CallLink(), start2, end2, 0);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

            if (checkLink.equalsIgnoreCase(getString(R.string.signup_link))){
                openActivity(RegisterActivity.class, true);
            }
        }
    }
}
