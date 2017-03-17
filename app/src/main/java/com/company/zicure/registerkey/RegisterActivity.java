package com.company.zicure.registerkey;

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

import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.dialog.DatePickerFragment;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.DateModel;
import com.company.zicure.registerkey.models.register.RegisterRequest;
import com.company.zicure.registerkey.models.UserModel;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.KeyboardUtil;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements View.OnFocusChangeListener, TextWatcher, EditText.OnEditorActionListener{

    @Bind(R.id.identity_card)
    EditText idCard;
    @Bind(R.id.birth_date)
    EditText birthDate;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.phone_number)
    EditText phoneNumber;

    private String strIdCard, strBirthDate, strPhone;
    //Context
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);
        birthDate.setOnFocusChangeListener(this);

        phoneNumber.addTextChangedListener(this);
        phoneNumber.setOnEditorActionListener(this);

        idCard.requestFocus();
        idCard.setOnEditorActionListener(this);

        if (savedInstanceState == null){
            setKey();
        }
    }

    private void setKey(){
        byte[] keyByte = Base64.decode(getString(R.string.staticKey).getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(keyByte);
    }

    @OnClick(R.id.btn_register)
    public void setBtnRegister() {
        checkInput();
    }

    private void checkInput(){
//        strIdCard = idCard.getText().toString().trim();
//        strBirthDate = birthDate.getText().toString().trim();
//        strPhone = phoneNumber.getText().toString().trim();

        strIdCard = "1200001551123";
        strBirthDate = "04-05-1993";
        strPhone = "082-445-6225";

        if (strIdCard.length() == 13 && !strBirthDate.isEmpty() && strPhone.length() == 12){
            RegisterRequest request = new RegisterRequest();
            RegisterRequest.Result result = request.new Result();
            result.setCitizenId(strIdCard);
            result.setBirthDate(strBirthDate);
            result.setPhone(strPhone);
            request.setResult(result);

            String str = new Gson().toJson(request);
            Log.d("RegisterRequest", str);

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String resultEncrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(request));
            Log.d("User", resultEncrypt);

            UserModel userModel = new UserModel();
            userModel.setUser(resultEncrypt);

            str = new Gson().toJson(userModel);
            Log.d("RegisterRequest", str);

            showLoadingDialog();
            ClientHttp.getInstance(context).register(userModel);

             Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
            if (strIdCard.length() < 13 ){
                idCard.requestFocus();
            }
            else if (phoneNumber.getText().toString().trim().length() < 12){
                phoneNumber.requestFocus();
            }
            else if (strBirthDate.isEmpty()){
                showDialog();
            }
        }
    }

    private void store(String strPhone){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.phone_key), strPhone);
        editor.commit();
    }

    @Subscribe
    public void onEvent(BaseResponse registerResponse){
        String str = new Gson().toJson(registerResponse);
        Log.d("registerResponse", str);

        BaseResponse.Result result = registerResponse.getResult();
        if (!result.getSuccess().isEmpty()){
            String[] arrStr = result.getSuccess().split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("EncryptCart", "DecryptData: " + decrypt);
            if (!decrypt.isEmpty()){
                Toast.makeText(this, decrypt, Toast.LENGTH_SHORT).show();

                store(strPhone);
                openActivity(OTPActivity.class, true);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        }else{
            Toast.makeText(getApplicationContext(), "" + registerResponse.getResult().getError(), Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }

    @Subscribe
    public void onEvent(DateModel date){
        String strDay = "", strMonth = "";
        if (date.getDay() < 10 || date.getMonth() < 10){
            strDay = "0" + date.getDay();
            strMonth = "0" + date.getMonth();
        }
        else{
            strDay = String.valueOf(date.getDay());
            strMonth = String.valueOf(date.getMonth());
        }
        birthDate.setText(strDay +"-"+strMonth+"-"+date.getYear());
        birthDate.setSelection(birthDate.getText().length());

        phoneNumber.requestFocus();
        KeyboardUtil.newInstance(this).getKeySoft().toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    private void showDialog(){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.birth_date)
    public void onClick(){
        showDialog();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            switch (view.getId()){
                case R.id.birth_date:
                    showDialog();
                    break;
            }
        }
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
        else if (actionId == EditorInfo.IME_ACTION_NEXT){
            if (idCard.getText().toString().trim().length() < 13){
                Toast.makeText(this, R.string.content_alert_idcard, Toast.LENGTH_LONG).show();
                idCard.requestFocus();
            }
        }

        return false;
    }
}
