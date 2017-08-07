package com.company.zicure.campusconnect.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.LabelView;
import com.company.zicure.campusconnect.interfaces.ItemClickListener;


/**
 * Created by 4GRYZ52 on 10/18/2016.
 */
public class MainMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //View
    public LabelView topicMenu;
    public ImageView imgBtnMenu;
    public RelativeLayout cardMenu;
    //Interface
    public ItemClickListener itemClickListener;

    public MainMenuHolder(View itemView) {
        super(itemView);
        topicMenu = (LabelView) itemView.findViewById(R.id.topic_menu);
        imgBtnMenu = (ImageView) itemView.findViewById(R.id.img_btn_menu);
        cardMenu = (RelativeLayout) itemView.findViewById(R.id.item_card_main_menu);

        itemView.setOnClickListener(this);
    }

    public void setItemOnClickListener(ItemClickListener itemOnClickListener){
        this.itemClickListener = itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemClick(v, getLayoutPosition());
    }
}
