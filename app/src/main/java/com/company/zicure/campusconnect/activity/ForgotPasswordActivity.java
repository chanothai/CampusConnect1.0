package com.company.zicure.campusconnect.activity;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.dialog.AwesomeDialogFragment;
import com.company.zicure.campusconnect.fragment.ForgotPassFragment;
import com.company.zicure.campusconnect.fragment.UpdatePasswordFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.updatepassword.ResponseForgotPassword;
import gallery.zicure.company.com.modellibrary.models.updatepassword.ResponseUpdatePassword;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;

public class ForgotPasswordActivity extends BaseActivity {

    /** Make: View **/
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /** Make: Properties **/

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
        transaction.replace(R.id.container, new ForgotPassFragment(), "ForgotPasswordFragment");
        transaction.commit();
    }

    private void initUpdatePasswordFragment(){
        FragmentManager manager = getSupportFragmentManager();
        ForgotPassFragment fragment = (ForgotPassFragment) manager.findFragmentByTag("ForgotPasswordFragment");
        String username = fragment.getUsername();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, UpdatePasswordFragment.newInstance(username, ""));
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
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

    /** Subscribe **/
    @Subscribe
    public void onEventResponseUpdatePassword(ResponseForgotPassword response) {
        if (response.getResultUpdate().getSuccess().equalsIgnoreCase("OK")){
            initUpdatePasswordFragment();
        }else{
            Toast.makeText(this, response.getResultUpdate().getError(), Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }

    @Subscribe
    public void onEventResponseUpdatePassword(ResponseUpdatePassword response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")) {
            Toast.makeText(this, response.getResult().getSuccess(), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, response.getResult().getError(), Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }
}
