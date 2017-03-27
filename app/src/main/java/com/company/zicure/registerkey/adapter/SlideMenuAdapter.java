package com.company.zicure.registerkey.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.fragment.AppMenuFragment;
import com.company.zicure.registerkey.holder.SlideMenuHolder;

import java.util.ArrayList;

import gallery.zicure.company.com.modellibrary.models.drawer.SlideMenuDetail;

/**
 * Created by 4GRYZ52 on 12/4/2016.
 */

abstract public class SlideMenuAdapter extends RecyclerView.Adapter<SlideMenuHolder> {

    private Context context = null;
    private ArrayList<SlideMenuDetail> arrMenu= null;

    public SlideMenuAdapter(Context context, ArrayList<SlideMenuDetail> arrMenu){
        this.context = context;
        this.arrMenu = arrMenu;
    }

    @Override
    public SlideMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_slide_menu, null);

        SlideMenuHolder slideMenuHolder = new SlideMenuHolder(view);
        return slideMenuHolder;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public int getItemCount() {
        return arrMenu.size();
    }

    public String getTitle(int positon){
        return arrMenu.get(positon).getTitle();
    }

    public int getImage(int position){
        return arrMenu.get(position).getImage();
    }
}
