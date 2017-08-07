package com.company.zicure.campusconnect.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.interfaces.ItemClickListener;

/**
 * Created by 4GRYZ52 on 12/4/2016.
 */

public class SlideMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //view
    public ImageView imgIcon;
    public TextView subTitle;

    public ItemClickListener itemClickListener;

    public SlideMenuHolder(View itemView) {
        super(itemView);
        imgIcon = (ImageView)itemView.findViewById(R.id.menu_icon);
        subTitle = (TextView)itemView.findViewById(R.id.sub_title);
        itemView.setOnClickListener(this);
    }

    public void setItemOnClickListener(ItemClickListener itemOnClickListener){
        this.itemClickListener = itemOnClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, getLayoutPosition());
    }
}
