package com.company.zicure.campusconnect.activity;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.dialog.AwesomeDialogFragment;
import com.company.zicure.campusconnect.fragment.ForgotPassFragment;
import com.company.zicure.campusconnect.fragment.UpdatePasswordFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.updatepassword.ResponseForgotPassword;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;

public class ForgotPasswordActivity extends BaseActivity implements AwesomeDialogFragment.OnDialogListener {

    /** Make: View **/
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /** Make: Properties **/
    private ResponseForgotPassword.ResultUpdate resultUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);

        setToolbar();
        initForgotPassFragment();
    }

    private void setToolbar(){
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbar, null, getDrawable(R.drawable.back_screen), null);
        }
    }

    private void initForgotPassFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ForgotPassFragment());
        transaction.commit();
    }

    private void initUpdatePasswordFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new UpdatePasswordFragment());
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Subscribe
    public void onEventResponseUpdatePassword(ResponseForgotPassword response) {
        resultUpdate = response.getResultUpdate();
        if (response.getResultUpdate().getSuccess().equalsIgnoreCase("OK")){
            showAlertDialog(false, "ข้อมูลถูกต้อง",true);
        }else{
            showAlertDialog(false, response.getResultUpdate().getError(), true);
        }

        dismissDialog();
    }

    private void showAlertDialog(boolean isSuccess, String message, boolean isIncorrectPIN) {
        String tag = "RegisterError";
        AwesomeDialogFragment.Builder builder = new AwesomeDialogFragment.Builder();

        builder.setTitle(R.string.title_dialog_update_password_th)
                .setMessage(message)
                .setPositive(R.string.dialog_update_button_positive)
                .setNegative(R.string.dialog_button_negative_th)
                .setPIN(isSuccess)
                .setIncorrectPIN(isIncorrectPIN);

        AwesomeDialogFragment fragment = builder.build();
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
    }

    @Override
    public void onPositiveButtonClick(String pinCode) {
        if (!resultUpdate.getSuccess().isEmpty()){
            initUpdatePasswordFragment();
        }
    }

    @Override
    public void onNegativeButtonClick() {

    }
}
