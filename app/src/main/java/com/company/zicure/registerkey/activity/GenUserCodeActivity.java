package com.company.zicure.registerkey.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.common.BaseActivity;
import com.company.zicure.registerkey.models.BaseResponse;
import com.company.zicure.registerkey.models.DataModel;
import com.company.zicure.registerkey.models.RequestUserCode;
import com.company.zicure.registerkey.models.ResponseUserCode;
import com.company.zicure.registerkey.models.UserRequest;
import com.company.zicure.registerkey.network.ClientHttp;
import com.company.zicure.registerkey.security.EncryptionAES;
import com.company.zicure.registerkey.utilize.EventBusCart;
import com.company.zicure.registerkey.utilize.ModelCart;
import com.company.zicure.registerkey.variables.VariableConnect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

public class GenUserCodeActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog = null;
    private String[] strArr = null;
    private String username = null;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_user_code);
        EventBusCart.getInstance().getEventBus().register(this);
        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            strArr = bundle.getStringArray(VariableConnect.userSecret);
            token = strArr[0];
            username = strArr[1];

            if (!token.isEmpty() && !username.isEmpty()){
                createDialog();
            }
        }
    }

    private void createDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_request_user_code);
        dialog.setCanceledOnTouchOutside(false);

        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        ImageView img = (ImageView) dialog.findViewById(R.id.img_mof_pay);
        img.setImageResource(R.drawable.logo_mof);

        dialog.show();
    }

    @Subscribe
    public void onEventGenUserCode(ResponseUserCode response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            ModelCart.getInstance().getDeviceToken().setResult(response.getResult());

            RequestUserCode request = new RequestUserCode();
            RequestUserCode.DeviceToken deviceToken = new RequestUserCode.DeviceToken();
            deviceToken.setUserCode(response.getResult().getDeviceToken().getUserCode());

            UserRequest user = new UserRequest();
            user.setToken(token);

            request.setDeviceToken(deviceToken);
            request.setUser(user);

            Log.d("RequestUserCode", new Gson().toJson(request));
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String encrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).encrypt(gson.toJson(request));

            if (!encrypt.isEmpty()){
                DataModel dataModel = new DataModel();
                dataModel.setData(encrypt);

                DataModel.User dataUser = new DataModel.User();
                dataUser.setUsername(username);
                dataModel.setUser(dataUser);

                ClientHttp.getInstance(this).approveDevice(dataModel);
            }
        }
    }

    @Subscribe
    public void onEventApproveDevice(BaseResponse response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            String eResult = response.getResult().geteResult();
            String[] spile = eResult.split(getString(R.string.key_iv));
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(spile[0], spile[1].getBytes());

            if (!decrypt.isEmpty()){
                decodeJson(decrypt);
            }
        }
    }

    private void decodeJson(String decrypt){
        try{
            JSONObject jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (success.equalsIgnoreCase("OK")){
                jsonObject = jsonObject.getJSONObject("AuthToken");
                String authCode = jsonObject.getString("auth_code");

                String packageName = "com.company.zicure.payment";
                String fullClassName = "com.company.zicure.payment.activity.MainActivity";
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(VariableConnect.authCode, authCode);
                intent.putExtras(bundle);
                intent.setComponent(new ComponentName(packageName, fullClassName));
                startActivity(intent);
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        dismissDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:{
                finish();
                dialog.cancel();
                break;
            }
            case R.id.btn_confirm:{
                showLoadingDialog();
                ClientHttp.getInstance(this).requestUserCode(VariableConnect.clientID);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }
}
