package com.company.zicure.campusconnect.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.dialog.AwesomeDialogFragment;
import com.company.zicure.campusconnect.dialog.DatePickerFragment;
import com.company.zicure.campusconnect.network.ClientHttp;
import com.company.zicure.campusconnect.security.EncryptionAES;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.register.RegisterRequest;
import gallery.zicure.company.com.modellibrary.models.register.ResponseRegister;
import gallery.zicure.company.com.modellibrary.models.register.ResponseUniversities;
import gallery.zicure.company.com.modellibrary.models.register.VerifyRequest;
import gallery.zicure.company.com.modellibrary.models.register.VerifyResponse;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

public class RegisterActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,View.OnFocusChangeListener, EditText.OnEditorActionListener, AwesomeDialogFragment.OnDialogListener, AdapterView.OnItemSelectedListener{

    /** Make: View **/
    @Bind(R.id.spinner_user_type)
    Spinner spUserType;
    @Bind(R.id.spinner_org)
    Spinner spUniversity;
    @Bind(R.id.layout_university)
    LinearLayout layoutUniversity;
    @Bind(R.id.citizen_id)
    EditText citizenId;
    @Bind(R.id.layout_screenName)
    LinearLayout layoutScreenName;
    @Bind(R.id.first_name)
    EditText firstName;
    @Bind(R.id.last_name)
    EditText lastName;
    @Bind(R.id.identity_card)
    EditText idCard;
    @Bind(R.id.birth_date)
    EditText birthDate;
    @Bind(R.id.txt_layout_id_person)
    TextInputLayout layoutIDPerson;
    @Bind(R.id.student_id)
    EditText studentID;
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
    @Bind(R.id.check_condition)
    CheckBox checkCondition;

    /** Make: Properties **/
    private int userTypeId, orgId;
    private List<String> userTypes = null;
    private boolean isCheckCondition = false;
    private String resultDate = null;
    //Context
    private Context context = this;
    private boolean isSuccess = false;
    private List<ResponseUniversities.ResultOrg.DataOrg.OrgName> listORG = null;
    private List<ResponseUniversities.ResultOrg.DataOrg.UserType> listUserType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);
        bindView();

        if (savedInstanceState == null){
            setKey();
            loadUniversity();
        }
    }

    private void bindView(){
        citizenId.requestFocus();
        birthDate.setOnFocusChangeListener(this);
        birthDate.setOnEditorActionListener(this);
        checkCondition.setOnCheckedChangeListener(this);
    }
    private void setKey(){
        byte[] keyByte = Base64.decode(VariableConnect.staticKey.getBytes(), Base64.NO_WRAP);
        ModelCart.getInstance().getKeyModel().setKey(keyByte);
    }

    private void loadUniversity(){
        ClientHttp.getInstance(this).requestORG();
    }


    /******** OnClick **************/
    @OnClick(R.id.btn_register)
    public void setBtnRegister() {
        checkInput();
    }

    private void checkInput(){
        RegisterRequest request = new RegisterRequest();
        RegisterRequest.User user = new RegisterRequest.User();
        user.setUserTypeId(Integer.toString(userTypeId));
        user.setCitizenId(citizenId.getText().toString().trim());
        user.setFirstName(firstName.getText().toString().trim());
        user.setLastName(lastName.getText().toString().trim());
        user.setBirthDate(birthDate.getText().toString().trim());
        user.setStdNo(studentID.getText().toString().trim());
        user.setOrgID(Integer.toString(orgId));
        user.setUsername(idCard.getText().toString().trim());
        user.setPhone(phoneNumber.getText().toString().trim());
        user.setPassword(editPass.getText().toString().trim());
        user.setRePassword(editConfirmPass.getText().toString().trim());
        user.setEmail(editEmail.getText().toString().trim());
        user.setAgree(isCheckCondition);
        request.setUser(user);

        showLoadingDialog();
        ClientHttp.getInstance(context).register(request);
    }

    @OnClick(R.id.birth_date)
    public void onClick(){
        showAlertDateDialog();
    }

    private void showAlertDateDialog(){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void store(String strPhone){
        SharedPreferences sharedPref = getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.phone_key), strPhone);
        editor.apply();
    }

    /************** Subscribe **************/
    @Subscribe
    public void onEventResponseUserType(ResponseUniversities response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            listORG = response.getResult().getData().getOrgNames();
            listUserType = response.getResult().getData().getUserTypes();

            userTypes = new ArrayList<>();
            for (int i = 0; i < response.getResult().getData().getUserTypes().size(); i++){
                userTypes.add(i, response.getResult().getData().getUserTypes().get(i).getUserTypeNameTH());
            }

            ArrayAdapter<String> adapterUserType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userTypes);
            spUserType.setAdapter(adapterUserType);
            spUserType.setOnItemSelectedListener(this);

            List<String> listOrg = new ArrayList<>();
            for (int j = 0; j < response.getResult().getData().getOrgNames().size(); j++){
                listOrg.add(j, response.getResult().getData().getOrgNames().get(j).getOrgNameTH());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOrg);
            spUniversity.setAdapter(adapter);
            spUniversity.setOnItemSelectedListener(this);
        }
    }

    @Subscribe
    public void onEventResponseRegister(ResponseRegister response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")) {
            isSuccess = true;
            showAlertDialog(isSuccess, "กรอกPIN CODE ที่ได้จากอีเมล์ของคุณ",false);
        }else{
            isSuccess = false;
            showAlertDialog(isSuccess, response.getResult().getError(),true);
        }

        dismissDialog();
    }

    private void showAlertDialog(boolean isSuccess, String message, boolean isIncorrectPIN) {
        String tag = "RegisterError";
        AwesomeDialogFragment.Builder builder = new AwesomeDialogFragment.Builder();

        builder.setTitle(R.string.dialog_title_pin_th)
                .setMessage(message)
                .setPositive(R.string.dialog_pin_button_positive_th)
                .setNegative(R.string.dialog_button_negative_th)
                .setPIN(isSuccess)
                .setIncorrectPIN(isIncorrectPIN);

        AwesomeDialogFragment fragment = builder.build();
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Subscribe
    public void onEventResponseRegisterSecure(BaseResponse registerResponse){
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
                store(phoneNumber.getText().toString().trim());
                Bundle bundle = new Bundle();
                bundle.putString("username", phoneNumber.getText().toString().trim());
                openActivity(LoginActivity.class,bundle, true);
            }else{
                String error = jsonObject.getString("Error");
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEventVerifyUser(VerifyResponse response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            finish();
        }else{
            showAlertDialog(true, response.getResult().getError(), true);
        }

        dismissDialog();
    }

    @Subscribe
    public void onEventDateTime(DateModel date){
        String strDay = "", strMonth = "";
        if (date.getMonth() < 10){
            strMonth = "0" + date.getMonth();
            strDay = getCurrentDay(date);
            resultDate = date.getYear() +"-"+strMonth+"-"+strDay;
            birthDate.setText(resultDate);
            birthDate.setSelection(birthDate.getText().length());
        }else{
            strMonth = String.valueOf(date.getMonth());
            strDay = getCurrentDay(date);
            resultDate = date.getYear() +"-"+strMonth+"-"+strDay;
            birthDate.setText(date.getYear() +"-"+strMonth+"-"+strDay);
            birthDate.setSelection(birthDate.getText().length());
        }
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

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == 0){
            birthDate.setText(resultDate);
        }

        return false;
    }

    @Override
    public void onPositiveButtonClick(String pinCode) {
        if (isSuccess) {
            if (pinCode != null){
                VerifyRequest request = new VerifyRequest();
                VerifyRequest.VerifyUser verifyUser = new VerifyRequest.VerifyUser();
                verifyUser.setPinCode(pinCode);
                verifyUser.setUsername(idCard.getText().toString().trim());
                request.setUser(verifyUser);

                showLoadingDialog();
                ClientHttp.getInstance(this).verifyUser(request);
            }
        }else{
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void onNegativeButtonClick() {
        finish();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            switch (view.getId()){
                case R.id.birth_date:
                    showAlertDateDialog();
                    break;
            }
        }
    }

    /** Spinner selected **/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_user_type:
                userTypeId = listUserType.get(position).getUserTypeId();
                if (listUserType.get(position).getUserTypeId() == 5){
                    layoutScreenName.setVisibility(View.VISIBLE);
                    layoutScreenName.setVisibility(View.VISIBLE);
                    layoutUniversity.setVisibility(View.GONE);
                    layoutIDPerson.setVisibility(View.GONE);
                }else{
                    layoutIDPerson.setVisibility(View.VISIBLE);
                    layoutScreenName.setVisibility(View.GONE);
                    layoutScreenName.setVisibility(View.GONE);
                    layoutUniversity.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.spinner_org:
                orgId = listORG.get(position).getOrgId();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /** Checkbox was check **/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            isCheckCondition = true;
            openActivity(ConditionActivity.class, false);
        }else{
            isCheckCondition = false;
        }
    }
}
