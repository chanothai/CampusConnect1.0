package gallery.zicure.company.com.gallery.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;

import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.gallery.R;
import gallery.zicure.company.com.gallery.adapter.GalleryGridViewAdapter;
import gallery.zicure.company.com.gallery.interfaces.ItemClickListener;
import gallery.zicure.company.com.gallery.model.ModelFile;
import gallery.zicure.company.com.gallery.util.ModelGalleryCart;
import gallery.zicure.company.com.gallery.util.PermissionManager;
import gallery.zicure.company.com.gallery.util.PermissionRequest;
import gallery.zicure.company.com.gallery.util.ResizeScreen;
import gallery.zicure.company.com.gallery.util.ScreenOrientation;
import gallery.zicure.company.com.gallery.view.StateGalleryRecyclerView;


public class GalleryActivity extends AppCompatActivity {
    private Parcelable recyclerViewState = null;
    private GalleryGridViewAdapter galleryAdapter = null;
    private StateGalleryRecyclerView recyclerGallery = null;
    //toolbar
    private Toolbar toolbar = null;
    private TextView title = null;
    private AppBarLayout appBarLayout = null;

    //value
    private int height = 0;
    private int width = 0;

    private int pageGallery = 0;
    private ModelFile modelFile = null;
    private String modelFileKey = "model_file_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        bindView();
        setToolbar();

        if (!PermissionRequest.newInstance(this).requestReadStorage()){
            addFolder();
        }
    }

    private void bindView(){
        recyclerGallery = (StateGalleryRecyclerView) findViewById(R.id.recyclerview_gallery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.title_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
    }


    private void setToolbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.height = getActionBarHeight() + getStatusBarHeight() + 10;
        appBarLayout.setLayoutParams(params);
        toolbar.setPadding(0, getStatusBarHeight(), 0,0);

        toolbar.setTitle("");
        setTitle();
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

    private void setTitle(){
        if (pageGallery == 0){
            title.setText(R.string.title_album_th);
        }
        else if (pageGallery == 1){
            title.setText(R.string.select_photo_th);
        }
    }

    private void addFolder(){
        try{
            if (modelFile == null){
                modelFile = new ModelFile();
            }
            ArrayList<ModelFile.Detail> arrFile = new ArrayList<ModelFile.Detail>();

            String directory = Environment.getExternalStorageDirectory()+ "/" + Environment.DIRECTORY_DCIM;
            File targetDirector = new File(directory);
            File[] files = targetDirector.listFiles();
            File[] imgFile;
            File newFile;
            for (int i = (files.length - 1); i >= 0; i--){
                newFile = files[i];
                String[] splitName = newFile.getAbsolutePath().split(Environment.DIRECTORY_DCIM + "/");
                if (splitName[1].equalsIgnoreCase("Camera") || splitName[1].equalsIgnoreCase("ScreenShort") || splitName[1].equalsIgnoreCase("Facebook")){
                    imgFile = newFile.listFiles();
                    ModelFile.Detail detail = new ModelFile.Detail();
                    for (int j = (imgFile.length - 1); j > (imgFile.length - 2); j--){
                        detail.setFile(imgFile[j]);
                    }
                    detail.setImgName(splitName[1]);
                    arrFile.add(detail);
                }
                Log.d("path", files[i].getAbsolutePath()+ " ," + splitName[1]);
            }

            modelFile.setArrFile(arrFile);
            pageGallery = 0;
            setGalleryAdapter(modelFile, pageGallery);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void addFileImage(String folderImage){
        try{
            if (modelFile == null){
                modelFile = new ModelFile();
            }
            ArrayList<ModelFile.Detail> arrDetail = new ArrayList<ModelFile.Detail>();
            String directory = Environment.getExternalStorageDirectory()+ "/" + Environment.DIRECTORY_DCIM + "/" + folderImage;
            File targetDirector = new File(directory);
            File[] files = targetDirector.listFiles();
            File newFile;
            for (int i = (files.length - 1); i >= 0; i--){
                newFile = files[i];

                ModelFile.Detail detail = new ModelFile.Detail();
                detail.setFile(newFile);
                arrDetail.add(detail);
            }
            modelFile.setArrFile(arrDetail);
            pageGallery = 1;
            setGalleryAdapter(modelFile, pageGallery);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void resizeImage(int page){
        ResizeScreen resizeScreen = new ResizeScreen(this);
        int orientation = new ScreenOrientation(this).getOrientation();
        if (page == 0){ // 0 = albums
            if (orientation == 1){
                recyclerGallery.setLayoutManager(new GridLayoutManager(this, 2));
                height = resizeScreen.widthScreen(2);
                width = resizeScreen.widthScreen(2);
            }
            else if (orientation == 2){
                recyclerGallery.setLayoutManager(new GridLayoutManager(this, 4));
                height = resizeScreen.widthScreen(4);
                width = resizeScreen.widthScreen(4);
            }
        }
        else if (page == 1){
            if (orientation == 1){
                recyclerGallery.setLayoutManager(new GridLayoutManager(this, 3));
                height = resizeScreen.widthScreen(3);
                width = resizeScreen.widthScreen(3);
            }
            else if (orientation == 2){
                recyclerGallery.setLayoutManager(new GridLayoutManager(this, 5));
                height = resizeScreen.widthScreen(5);
                width = resizeScreen.widthScreen(5);
            }
        }
    }

    private void setGalleryAdapter(ModelFile modelFile, int page){
        setTitle();
        resizeImage(page);
        galleryAdapter = new GalleryGridViewAdapter(modelFile.getArrFile(), this) {
            @Override
            public void onBindViewHolder(final GalleryGridViewHolder holder, int position) {
                if (pageGallery == 0){
                    holder.title.setVisibility(ImageView.VISIBLE);
                    holder.title.setText(getTitle(position));
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            addFileImage(getTitle(position));
                        }
                    });
                }else{
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ModelGalleryCart.getInstatnce().getModelFile().setDetail(getDetailFile(position)); //Create image share

                            startShareActivity(holder.imgGallery);
                        }
                    });
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imgGallery.getLayoutParams();
                params.height = height;
                params.width = width;
                holder.imgGallery.setLayoutParams(params);

                Context context = holder.imgGallery.getContext();
                File file = getFile(position);

                Glide.with(context)
                        .load(file)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imgGallery);
            }

            private void startShareActivity(View view){
                Intent intent = new Intent(GalleryActivity.this, ShareImageActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "shareView").toBundle());
                }else{
                    startActivity(intent);
                }
            }
        };
        recyclerGallery.setAdapter(galleryAdapter);
        recyclerGallery.setItemAnimator(new DefaultItemAnimator());
        galleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionKeyNumber.getInstance().getPermissionReadStorageKey() == requestCode){
            try {
                if (grantResults[0] != -1){
                    addFolder();
                }else{
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ss", Parcels.wrap(pageGallery));
        outState.putParcelable(modelFileKey, Parcels.wrap(modelFile));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pageGallery = Parcels.unwrap(savedInstanceState.getParcelable("ss"));
        modelFile = Parcels.unwrap(savedInstanceState.getParcelable(modelFileKey));
        setGalleryAdapter(modelFile, pageGallery);
    }

    @Override
    public void onBackPressed() {
        if (pageGallery == 1){
            pageGallery = 0;
            addFolder();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if (pageGallery == 0){
                finish();
            }

            pageGallery = 0;
            addFolder();
        }
        return super.onOptionsItemSelected(item);
    }
}
