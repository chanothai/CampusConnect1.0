package gallery.zicure.company.com.gallery.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gallery.zicure.company.com.gallery.activity.GalleryActivity;
import gallery.zicure.company.com.gallery.util.PermissionKeyNumber;
import gallery.zicure.company.com.gallery.R;
import gallery.zicure.company.com.gallery.adapter.RecyclerViewAdapter;
import gallery.zicure.company.com.gallery.interfaces.ItemClickListener;
import gallery.zicure.company.com.gallery.util.PermissionRequest;

/**
 * Created by 4GRYZ52 on 3/15/2017.
 */

public class DialogSelectGallery {
    private Dialog dialog = null;
    private Activity activity = null;
    private RecyclerViewAdapter adapter = null;
    private RecyclerView recyclerView = null;

    public DialogSelectGallery(Activity activity){
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog(){
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_select_gallery);

        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerview_select_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        setAdapterView();

        dialog.show();
    }

    private void setAdapterView(){
        List<String> listTitle = new ArrayList<String>();
        listTitle.add(0, activity.getString(R.string.take_photo_th));
        listTitle.add(1, activity.getString(R.string.select_album_th));
        adapter = new RecyclerViewAdapter(listTitle) {
            @Override
            public void onBindViewHolder(RecyclerViewHolder holder, int position) {
                holder.title.setText(getTitle(position));
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.take_photo_th))){
                            if (!PermissionRequest.newInstance(activity).requestCamera()){
                                intentCamera();
                            }
                        }

                        if (getTitle(position).equalsIgnoreCase(activity.getString(R.string.select_album_th))){
                            Intent intent = new Intent(activity, GalleryActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void intentCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, PermissionKeyNumber.getInstance().getPermissionCameraKey());
    }
}
