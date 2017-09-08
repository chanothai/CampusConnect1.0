package com.company.zicure.campusconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.LabelView;
import com.company.zicure.campusconnect.holder.IDCardHolder;

import org.json.JSONObject;

import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard.ResultProfile.ProfileData;

/**
 * Created by ballomo on 7/30/2017 AD.
 */

public class IDCardAdapter extends RecyclerView.Adapter<IDCardHolder> {

    private ProfileData information = null;

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
        try{
            if (!information.getCitizenId().isEmpty()) holder.citizenId.setText(information.getCitizenId());
        }catch (NullPointerException e){
            setHideData(holder.citizenId);
        }

        try{
            if (!information.getCardNo().equals(JSONObject.NULL)) holder.personCard.setText(information.getCardNo());
        } catch (NullPointerException e) {
            setHideData(holder.personCard);
        }

        try{
            if (!information.getOrganization().equals(JSONObject.NULL)) holder.organization.setText(information.getOrganization());
        } catch (NullPointerException e) {
            setHideData(holder.organization);
        }
        try{
            if (!information.getPositionEdu().equals(JSONObject.NULL)) holder.eduPosition.setText(information.getPositionEdu());
        } catch (NullPointerException e) {
            setHideData(holder.eduPosition);
        }

        try{
            if (!information.getPositionOrg().equals(JSONObject.NULL)) holder.orgPosition.setText(information.getPositionOrg());
        } catch (NullPointerException e) {
            setHideData(holder.orgPosition);
        }
        try{
            if (!information.getDepartment().equals(JSONObject.NULL)) holder.department.setText(information.getDepartment());
        } catch (NullPointerException e) {
            setHideData(holder.department);
        }
        try{
            if (!information.getSection().equals(JSONObject.NULL)) holder.section.setText(information.getSection());
        } catch (NullPointerException e) {
            setHideData(holder.section);
        }
        try{
            if (!information.getMobile().equals(JSONObject.NULL)) holder.phone.setText(information.getMobile());
        } catch (NullPointerException e) {
            setHideData(holder.phone);
        }

        try{
            if (!information.getYearLavel().equals(JSONObject.NULL)) holder.yearLavel.setText(information.getYearLavel());
        } catch (NullPointerException e) {
            setHideData(holder.yearLavel);
        }

        try{
            if (!information.getStudyStart().equals(JSONObject.NULL)) holder.studyStart.setText(information.getStudyStart());
        } catch (NullPointerException e) {
            setHideData(holder.studyStart);
        }
        try{
            if (!information.getEmail().equals(JSONObject.NULL)) holder.email.setText(information.getEmail());
        } catch (NullPointerException e) {
            setHideData(holder.email);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private void setHideData(LabelView information){
        information.setVisibility(View.GONE);
    }
}
