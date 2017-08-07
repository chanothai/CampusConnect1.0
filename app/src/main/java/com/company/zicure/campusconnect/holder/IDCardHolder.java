package com.company.zicure.campusconnect.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.LabelView;


/**
 * Created by ballomo on 7/30/2017 AD.
 */

public class IDCardHolder extends RecyclerView.ViewHolder {
    public LabelView information;

    public IDCardHolder(View itemView) {
        super(itemView);
        information = (LabelView) itemView.findViewById(R.id.label_information);
    }
}
