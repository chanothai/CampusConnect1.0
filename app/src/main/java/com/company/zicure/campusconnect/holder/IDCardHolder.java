package com.company.zicure.campusconnect.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.LabelView;


/**
 * Created by ballomo on 7/30/2017 AD.
 */

public class IDCardHolder extends RecyclerView.ViewHolder {
    public LabelView citizenId;
    public LabelView personCard;
    public LabelView organization;
    public LabelView eduPosition;
    public LabelView orgPosition;
    public LabelView department;
    public LabelView section;
    public LabelView yearLavel;
    public LabelView studyStart;
    public LabelView email;
    public LabelView phone;

    public IDCardHolder(View itemView) {
        super(itemView);
        citizenId = (LabelView) itemView.findViewById(R.id.citizen_id);
        personCard = (LabelView) itemView.findViewById(R.id.person_card_no);
        organization = (LabelView) itemView.findViewById(R.id.organization);
        eduPosition = (LabelView) itemView.findViewById(R.id.position_edu);
        orgPosition = (LabelView) itemView.findViewById(R.id.position_org);
        department = (LabelView) itemView.findViewById(R.id.department);
        section = (LabelView) itemView.findViewById(R.id.section);
        yearLavel = (LabelView) itemView.findViewById(R.id.year_level);
        studyStart = (LabelView) itemView.findViewById(R.id.study_start);
        email = (LabelView) itemView.findViewById(R.id.email);
        phone = (LabelView) itemView.findViewById(R.id.moblie_no);
    }
}
