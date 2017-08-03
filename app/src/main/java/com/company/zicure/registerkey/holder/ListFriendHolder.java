package com.company.zicure.registerkey.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.customView.LabelView;
import com.joooonho.SelectableRoundedImageView;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class ListFriendHolder extends RecyclerView.ViewHolder{

    /** Make: View **/
    public SelectableRoundedImageView imgFriend;
    public ImageButton btnPhoneNo;
    public LabelView nameFriend;
    public LabelView sectionFriend;

    public ListFriendHolder(View itemView) {
        super(itemView);
        imgFriend = (SelectableRoundedImageView) itemView.findViewById(R.id.img_friend);
        btnPhoneNo = (ImageButton) itemView.findViewById(R.id.img_call_friend);
        nameFriend = (LabelView) itemView.findViewById(R.id.name_friend);
        sectionFriend = (LabelView) itemView.findViewById(R.id.section_friend);
    }
}
