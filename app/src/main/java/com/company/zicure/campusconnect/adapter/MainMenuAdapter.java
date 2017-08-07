package com.company.zicure.campusconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.holder.MainMenuHolder;
import com.company.zicure.campusconnect.R;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;

/**
 * Created by 4GRYZ52 on 10/18/2016.
 */
abstract public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuHolder> {

    //Properties
    private Context context = null;
    private List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl.BlocUser> arrData = null;

    //Constructor
    public MainMenuAdapter(Context context, List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl.BlocUser> arrData){
        this.context = context;
        this.arrData = arrData;
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

    public Context getContext(){
        return context;
    }

    public List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl.BlocUser> getData(){
        return arrData;
    }

    @Override
    public int getItemCount() {
        try {
            if (arrData.size() > 0) {
                return arrData.size();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }
}
