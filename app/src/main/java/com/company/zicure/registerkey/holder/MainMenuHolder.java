package com.company.zicure.registerkey.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.interfaces.ItemClickListener;


/**
 * Created by 4GRYZ52 on 10/18/2016.
 */
public class MainMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //View
    public TextView topicMenu;
    public ImageView imgBtnMenu;
    public RelativeLayout cardMenu;
    //Interface
    public ItemClickListener itemClickListener;

    public MainMenuHolder(View itemView) {
        super(itemView);
        topicMenu = (TextView) itemView.findViewById(R.id.topic_menu);
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
