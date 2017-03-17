package gallery.zicure.company.com.gallery.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joooonho.SelectableRoundedImageView;

import java.io.File;


import gallery.zicure.company.com.gallery.R;
import gallery.zicure.company.com.gallery.util.ModelGalleryCart;
import gallery.zicure.company.com.gallery.util.ResizeScreen;

public class ShareImageActivity extends AppCompatActivity {

    private Toolbar toolbar = null;
    private SelectableRoundedImageView imgShare = null;
    private TextView titleToolbar = null;
    private Button btnSetProfile = null;
    private AppBarLayout appBarLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_image);
        bindView();

        resizeImage();
        setImageShare();
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) toolbar.findViewById(R.id.title_toolbar);
        btnSetProfile = (Button) toolbar.findViewById(R.id.btn_use_profile);
        imgShare = (SelectableRoundedImageView) findViewById(R.id.imageShare);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        setToolbar();
    }

    private void setToolbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.height = getActionBarHeight() + getStatusBarHeight() + 10;
        appBarLayout.setLayoutParams(params);
        toolbar.setPadding(0, getStatusBarHeight(), 0,0);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_close);
        titleToolbar.setText(R.string.profile_picture);
        btnSetProfile.setText(R.string.use_th);
        btnSetProfile.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int getActionBarHeight(){
        int actionBarHeight = 0;
        final TypedArray styleAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styleAttributes.getDimension(0,0);
        styleAttributes.recycle();

        return actionBarHeight;
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void resizeImage(){
        ResizeScreen resizeScreen = new ResizeScreen(this);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgShare.getLayoutParams();
        params.height = resizeScreen.heightScreen(3);
        params.width = resizeScreen.heightScreen(3);
        imgShare.setLayoutParams(params);
    }


    private void setImageShare(){
        ResizeScreen resizeScreen = new ResizeScreen(this);
        File file = ModelGalleryCart.getInstatnce().getModelFile().getDetail().getFile();
        Glide.with(getApplicationContext())
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(768,768)
                .centerCrop()
                .into(imgShare);

        //.override(768,432)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
