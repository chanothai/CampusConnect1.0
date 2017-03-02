package com.company.zicure.registerkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.registerkey.holder.MainMenuHolder;
import com.company.zicure.registerkey.R;

/**
 * Created by 4GRYZ52 on 10/18/2016.
 */
abstract public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuHolder> {

    //Properties
    private Context context = null;
    private String[] menus = null;
    private int[] imgs = null;

    //Constructor
    public MainMenuAdapter(Context context, String[] menus, int[] imgs){
        this.context = context;
        this.menus = menus;
        this.imgs = imgs;
    }

    //When ViewHolder is being create
    @Override
    public MainMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate xml and hold in view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_main_menu,null);

        //Holder
        MainMenuHolder mainMenuHolder = new MainMenuHolder(view);

        return mainMenuHolder;
    }

    public String getItemName(int postion){
        return menus[postion];
    }

    public Context getContext(){
        return context;
    }


    @Override
    public int getItemCount() {
        return menus.length;
    }
}
