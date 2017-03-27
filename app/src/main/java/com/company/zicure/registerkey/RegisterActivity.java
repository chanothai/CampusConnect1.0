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
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.register.RegisterRequest;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.KeyboardUtil;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

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
        byte[] keyByte = Base64.decode(VariableConnect.staticKey.getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(keyByte);
    }

    @OnClick(R.id.btn_register)
    public void setBtnRegister() {
        checkInput();
    }

    private void checkInput(){
        strIdCard = idCard.getText().toString().trim();
        strBirthDate = birthDate.getText().toString().trim();
        strPhone = phoneNumber.getText().toString().trim();

        if (strIdCard.length() == 13 && !strBirthDate.isEmpty() && strPhone.length() == 12){

            String[] phone = strPhone.split("-");
            String currentPhone = phone[0] + phone[1] + phone[2];

            RegisterRequest request = new RegisterRequest();
            RegisterRequest.User user = new RegisterRequest.User();
            user.setCitizenId(strIdCard);
            user.setBirthDate(strBirthDate);
            user.setPhone(currentPhone);
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

            showLoadingDialog();
            ClientHttp.getInstance(context).register(dataModel);

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
            String[] arrStr = result.geteResult().split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(arrStr[0], arrStr[1].getBytes());//(text, key
            Log.d("EncryptCart", "DecryptData: " + decrypt);

            if (decrypt != null){
                decodeJson(decrypt);
            }
        }else{
            Toast.makeText(getApplicationContext(), "" + registerResponse.getResult().getError(), Toast.LENGTH_LONG).show();
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
                openActivity(OTPActivity.class,bundle, true);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }else{
                String error = jsonObject.getString("Error");
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onEvent(DateModel date){
        String strDay = "", strMonth = "";

        if (date.getMonth() < 10){
            strMonth = "0" + date.getMonth();
            strDay = getCurrentDay(date);
            birthDate.setText(date.getYear() +"-"+strMonth+"-"+strDay);
            birthDate.setSelection(birthDate.getText().length());
        }else{
            strMonth = String.valueOf(date.getMonth());
            strDay = getCurrentDay(date);
            birthDate.setText(date.getYear() +"-"+strMonth+"-"+strDay);
            birthDate.setSelection(birthDate.getText().length());
        }

        phoneNumber.requestFocus();
        KeyboardUtil.newInstance(this).getKeySoft().toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        dismissDialog();
    }

    private String getCurrentDay(DateModel date){
        String strDay = "";
        if (date.getDay() < 10){
            strDay = "0" + date.getDay();
        }else{
            strDay = String.valueOf(date.getDay());
        }

        return strDay;
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
