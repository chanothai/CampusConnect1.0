package com.company.zicure.registerkey.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.activity.LoginActivity;
import com.company.zicure.registerkey.dialog.DatePickerFragment;
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
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.register.RegisterRequest;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.KeyboardUtil;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class RegisterActivity extends BaseActivity implements TextWatcher, EditText.OnEditorActionListener{

    @Bind(R.id.identity_card)
    EditText idCard;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.phone_number)
    EditText phoneNumber;
    @Bind(R.id.edit_password)
    EditText editPass;
    @Bind(R.id.edit_confirm_password)
    EditText editConfirmPass;
    @Bind(R.id.edit_email)
    EditText editEmail;

    private String strIdCard, strPhone, pass, confirmPass, email;
    //Context
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);

        phoneNumber.addTextChangedListener(this);
        phoneNumber.setOnEditorActionListener(this);

        idCard.requestFocus();
        idCard.setOnEditorActionListener(this);

        if (savedInstanceState == null){
            setKey();
        }
    }

    private void setKey(){
        byte[] keyByte = Base64.decode(VariableConnect.staticKey.getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(keyByte);
    }

    @OnClick(R.id.btn_register)
    public void setBtnRegister() {
        checkInput();
    }

    private void checkInput(){
        strIdCard = idCard.getText().toString().trim();
        strPhone = phoneNumber.getText().toString().trim();
        pass = editPass.getText().toString().trim();
        confirmPass = editConfirmPass.getText().toString().trim();
        email = editEmail.getText().toString().trim();

        if (confirmPass.equalsIgnoreCase(pass) && !confirmPass.isEmpty() && !pass.isEmpty()){
            if (strIdCard.length() == 13 && strPhone.length() == 12 && !email.isEmpty()){
                String[] phone = strPhone.split("-");
                String currentPhone = phone[0] + phone[1] + phone[2];

                DataModel dataModel = createModel(currentPhone);

                showLoadingDialog();
                ClientHttp.getInstance(context).register(dataModel);
            }
        }else{
            editConfirmPass.requestFocus();
            Toast.makeText(getApplicationContext(), R.string.message_check_password_th, Toast.LENGTH_SHORT).show();
        }
    }

    private DataModel createModel(String currentPhone){
        RegisterRequest request = new RegisterRequest();
        RegisterRequest.User user = new RegisterRequest.User();
        user.setCitizenId(strIdCard);
        user.setPhone(currentPhone);
        user.setPassword(pass);
        user.setRePassword(confirmPass);
        user.setEmail(email);

        request.setUser(user);

        String str = new Gson().toJson(request);
        Log.d("RegisterRequest", str);

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(request));
        Log.d("User", resultEncrypt);

        DataModel dataModel = new DataModel();
        dataModel.setData(resultEncrypt);

        str = new Gson().toJson(dataModel);
        Log.d("RegisterRequest", str);

        return dataModel;
    }

    private void store(String strPhone){
        SharedPreferences sharedPref = getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.phone_key), strPhone);
        editor.apply();
    }

    @Subscribe
    public void onEvent(BaseResponse registerResponse){
        try{
            String str = new Gson().toJson(registerResponse);
            Log.d("registerResponse", str);

            BaseResponse.Result result = registerResponse.getResult();
            if (!result.getSuccess().isEmpty()){
                String[] arrStr = result.geteResult().split(getString(R.string.key_iv));
                String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
                Log.d("EncryptCart", "DecryptData: " + decrypt);

                if (decrypt != null){
                    decodeJson(decrypt);
                }
            }else{
                Toast.makeText(getApplicationContext(), "" + registerResponse.getResult().getError(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        dismissDialog();
    }

    private void decodeJson(String decrypt){
        try{
            JSONObject jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (!success.isEmpty()){
                Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
                store(strPhone);
                Bundle bundle = new Bundle();
                bundle.putString("username", strPhone);
                openActivity(LoginActivity.class,bundle, true);
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int position, int i1, int i2) {
        String txtResult =  "";
        try {
            if (position == 2){
                txtResult = phoneNumber.getText().toString().trim() + "-";
                phoneNumber.setText(txtResult);
                phoneNumber.setSelection(4);
            }
            else if (position == 6){
                txtResult = phoneNumber.getText().toString().trim() + "-";
                phoneNumber.setText(txtResult);
                phoneNumber.setSelection(8);
            }
            else if (position == 7){
                if (!txtResult.equalsIgnoreCase("-")){
                    txtResult = "-" + txtResult;
                    phoneNumber.setText(txtResult);
                    phoneNumber.setSelection(9);
                }
            }
        }catch (Exception e){
            phoneNumber.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE){
            checkInput();
        }

        return false;
    }
}
