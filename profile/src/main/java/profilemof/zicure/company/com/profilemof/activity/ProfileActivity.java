package profilemof.zicure.company.com.profilemof.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import de.hdodenhof.circleimageview.CircleImageView;
import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.gallery.dialog.DialogSelectGallery;
import profilemof.zicure.company.com.profilemof.R;
import profilemof.zicure.company.com.profilemof.adapter.ViewPagerAdapter;
import profilemof.zicure.company.com.profilemof.fragment.AddCashFragment;
import profilemof.zicure.company.com.profilemof.fragment.UserDetailFragment;
import profilemof.zicure.company.com.profilemof.utilize.ModelCartProfile;
import profilemof.zicure.company.com.profilemof.utilize.ResizeScreen;

public class ProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{
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
        bindView();
        setToolbar();
        setStatusBarTint();
        setupViewPager(viewPager);
        initialTab();
        setImgProfile();

        if (savedInstanceState == null){

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

    private void setStatusBarTint(){
        if (Build.VERSION.SDK_INT < 21){
            //create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            //enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            //enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
            //set the transparent color of the status bar 20% darker
            tintManager.setTintColor(Color.parseColor("#20000000"));
        }
    }

    private void initialTab(){
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
        tabLayout.setOnTabSelectedListener(this);
    }

    private void setImgProfile(){
        try{
            imgEditProfile.setImageResource(R.drawable.ic_google_images);
            if (ModelCartProfile.getInstance().getAccount().equals(getString(R.string.account1))){
                imgProfile.setImageResource(R.drawable.base);
                accountProfile.setText(getString(R.string.account1));
            }
            else if (ModelCartProfile.getInstance().getAccount().equals(getString(R.string.account2))){
                imgProfile.setImageResource(R.drawable.yajai);
                accountProfile.setText(getString(R.string.account2));
            }
        }catch (NullPointerException e){
            imgProfile.setImageResource(R.mipmap.ic_launcher);
            accountProfile.setText("");
        }
    }

    private void setToolbar(){
        toolbar.setTitle("");
        //Set the padding to math the status bar height
        toolbar.setPadding(0, getStatusBarHeight() - 8, 0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserDetailFragment(), getString(R.string.data_title));
        adapter.addFragment(new AddCashFragment(), getString(R.string.add_cash_title));
        viewPager.setAdapter(adapter);
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
    public void onClick(View view) {
        if (view.getId() == R.id.img_edit_profile){
            DialogSelectGallery dialog = new DialogSelectGallery(this);
            dialog.showDialog();
        }
    }
}

