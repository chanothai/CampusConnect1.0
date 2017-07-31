package com.company.zicure.registerkey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.holder.IDCardHolder;

import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard.ResultProfile.ProfileData;

/**
 * Created by ballomo on 7/30/2017 AD.
 */

public class IDCardAdapter extends RecyclerView.Adapter<IDCardHolder> {

    private ProfileData information = null;
    private String title;

    public IDCardAdapter(ProfileData information) {
        this.information = information;
    }

    @Override
    public IDCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate xml and hold in view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_id_card,null);

        IDCardHolder holder = new IDCardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IDCardHolder holder, int position) {
        switch (position){
            case 0:
                title = "เลขประจำตัว : " + information.getProfileID();
                holder.information.setText(title);
                break;
            case 1:
                title = "มหาวิทยาลัย : " + information.getOrganization();
                holder.information.setText(title);
                break;
            case 2:
                title = "คณะ : " + information.getDepartment();
                holder.information.setText(title);
                break;
            case 3:
                title = "สาขา : " + information.getSection();
                holder.information.setText(title);
                break;
            case 4:
                title = "อีเมล : " + information.getEmail();
                holder.information.setText(title);
                break;
            case 5:
                title = "เบอร์ : " + information.getMobile();
                holder.information.setText(title);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
