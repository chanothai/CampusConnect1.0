package profilemof.zicure.company.com.profilemof.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
import profilemof.zicure.company.com.profilemof.R;
import profilemof.zicure.company.com.profilemof.adapter.ViewPagerAdapter;
import profilemof.zicure.company.com.profilemof.fragment.UserDetailFragment;
import profilemof.zicure.company.com.profilemof.security.EncryptionAES;

public class ProfileActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{
    private Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private SelectableRoundedImageView imgProfile = null;
    private CircleImageView imgEditProfile = null;
    private TextView accountProfile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EventBusCart.getInstance().getEventBus().register(this);
        bindView();
        setToolbar();

        if (savedInstanceState == null){
            setImgProfile();

            setupViewPager(viewPager);
        }
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgProfile = (SelectableRoundedImageView) findViewById(R.id.img_profile);
        accountProfile = (TextView) findViewById(R.id.account_profile);
        imgEditProfile = (CircleImageView) findViewById(R.id.img_edit_profile);
        imgEditProfile.setOnClickListener(this);

        resizeImage();
    }

    private void resizeImage(){
        ResizeScreen resizeScreen = new ResizeScreen(this);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgProfile.getLayoutParams();
        params.height = resizeScreen.widthScreen(3);
        params.width = resizeScreen.widthScreen(3);
        imgProfile.setLayoutParams(params);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) imgEditProfile.getLayoutParams();
        params2.height = imgProfile.getLayoutParams().height / 3;
        params2.width = imgProfile.getLayoutParams().width / 3;
        imgEditProfile.setLayoutParams(params2);
    }



    private void setToolbar(){
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setToolbar(toolbar, null, null, null);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserDetailFragment(), getString(R.string.data_title));
        adapter.addFragment(new ActivateFragment(), getString(R.string.add_cash_title));
        viewPager.setAdapter(adapter);

        initialTab();
    }

    private void initialTab(){
        Bundle bundle = getIntent().getExtras();
        int page = bundle.getInt(VariableConnect.pageKey);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(page);
        tab.select();
        tabLayout.setOnTabSelectedListener(this);
    }

    private void setImgProfile(){
        try{
            imgEditProfile.setImageResource(R.drawable.ic_google_images);
            Glide.with(this)
                    .load(ModelCart.getInstance().getUserBloc().getResult().getData().getUserInfo().getImgPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgProfile);

            String screenName = ModelCart.getInstance().getUserBloc().getResult().getData().getUserInfo().getFirstNameTH();
            accountProfile.setText(screenName);

        }catch (NullPointerException e){
            imgProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            accountProfile.setText("");
        }
    }

    @Subscribe
    public void onEventAutoToken(BaseResponse response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            String eResult = response.getResult().geteResult();
            String[] spilt = eResult.split(VariableConnect.keyIV);
            String decrypt = EncryptionAES.newInstance(ModelCart.getInstance().getKeyModel().getKey()).decrypt(spilt[0], spilt[1].getBytes());

            if (!decrypt.isEmpty()){
                decodeJson(decrypt);
            }
        }else{
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }

    private void decodeJson(String decrypt){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(decrypt);
            String success = jsonObject.getString("Success");
            if (success.equalsIgnoreCase("OK")){
                jsonObject = jsonObject.getJSONObject("AuthToken");
                String authCode = jsonObject.getString("auth_code");
                ModelCart.getInstance().getKeyModel().setAuthToken(authCode); //save auth token

                Toast.makeText(this, R.string.auth_token_complete_th, Toast.LENGTH_SHORT).show();

                String authToken = null;
                String strPackage = "com.company.zicure.payment";
                authToken = ModelCart.getInstance().getKeyModel().getAuthToken();
                Intent intent = getPackageManager().getLaunchIntentForPackage(strPackage);
                if (authToken != null){
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, authToken);
                }

                startActivity(intent);
                ModelCart.getInstance().getKeyModel().setAuthToken("");
            }
        }catch (Exception e){
            try {
                String error = jsonObject.getString("Error");
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionKeyNumber.getInstance().getPermissionCameraKey() == requestCode){
            if (grantResults[0] != -1){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PermissionKeyNumber.getInstance().getPermissionCameraKey());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1){
            Log.d("Activity Result", requestCode +"," + resultCode +"," + data.toString());
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void finishActivity(){
        finish();
        overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_edit_profile){
//            DialogSelectGallery dialog = new DialogSelectGallery(this);
//            dialog.showDialog();
        }
    }
}

