package gallery.zicure.company.com.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcel;

import java.io.File;
import java.util.ArrayList;

import gallery.zicure.company.com.gallery.R;
import gallery.zicure.company.com.gallery.interfaces.ItemClickListener;
import gallery.zicure.company.com.gallery.model.ModelFile;
import gallery.zicure.company.com.gallery.view.ImageGalleryView;

/**
 * Created by 4GRYZ52 on 3/12/2017.
 */

public abstract class GalleryGridViewAdapter extends RecyclerView.Adapter<GalleryGridViewAdapter.GalleryGridViewHolder>{

    private ArrayList<ModelFile.Detail> arrFile = null;
    private Activity context = null;
    public GalleryGridViewAdapter(ArrayList<ModelFile.Detail> arrFile, Activity context){
        this.context = context;
        this.arrFile = arrFile;
    }

    public Activity getActivity(){
        return context;
    }

    public ModelFile.Detail getDetailFile(int position){
        return arrFile.get(position);
    }

    public File getFile(int position){
        return arrFile.get(position).getFile();
    }

    public String getTitle(int position){
        return arrFile.get(position).getImgName();
    }

    @Override
    public GalleryGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_gallery, parent, false);
        GalleryGridViewHolder holder = new GalleryGridViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return arrFile.size();
    }

    public class GalleryGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageGalleryView imgGallery;
        public TextView title;
        ItemClickListener itemClickListener;
        public GalleryGridViewHolder(View itemView) {
            super(itemView);
            imgGallery = (ImageGalleryView) itemView.findViewById(R.id.imgGallery);
            title = (TextView) itemView.findViewById(R.id.titleImg);

            imgGallery.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
}
