package com.company.zicure.campusconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.activity.IDCardActivity;
import com.company.zicure.campusconnect.holder.ListFriendHolder;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.contact.ResponseContactList.ResultContactList.ContactListData;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendHolder> {

    private CallPhoneListener callPhoneListener = null;
    public List<ContactListData> contactList;
    private Context context = null;

    public ListFriendAdapter(Context context, List<ContactListData> contactList, CallPhoneListener callPhoneListener) {
        this.contactList = contactList;
        this.context = context;
        this.callPhoneListener = callPhoneListener;
    }

    @Override
    public ListFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_friend, null);

        ListFriendHolder listFriendHolder = new ListFriendHolder(view);
        return listFriendHolder;
    }

    @Override
    public void onBindViewHolder(ListFriendHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(VariableConnect.TITLE_CATEGORY , context.getString(R.string.title_activity_id_card_th));
                bundle.putString("user_id", Integer.toString(contactList.get(position).getUserPersonal().getId()));
                Intent intent = new Intent(context, IDCardActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        if (contactList.get(position).getUserPersonal().getImgPath() != null) {
            Glide.with(context)
                    .load(contactList.get(position).getUserPersonal().getImgPath())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgFriend);
        } else {
            holder.imgFriend.setImageResource(R.drawable.people);
        }

        String screenName = contactList.get(position).getUserPersonal().getFirstNameTH() + " " + contactList.get(position).getUserPersonal().getLastNameTH();
        holder.nameFriend.setText(screenName);

        holder.sectionFriend.setText(contactList.get(position).getUserPersonal().getDepartment());

        holder.btnPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNo = contactList.get(position).getUserPersonal().getMobileNo();
                if (mobileNo != null) {
                    callPhoneListener.getMobileNo(mobileNo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contactList.size() > 0){
            return contactList.size();
        }
        return 0;
    }

    public interface CallPhoneListener{
        void getMobileNo(String mobile);
    }
}

