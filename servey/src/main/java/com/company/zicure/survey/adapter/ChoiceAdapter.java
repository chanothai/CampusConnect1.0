package com.company.zicure.survey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.company.zicure.survey.R;
import com.company.zicure.survey.interfaces.ItemClickListener;
import com.company.zicure.survey.utilize.ModelCart;

/**
 * Created by 4GRYZ52 on 1/11/2017.
 */

public abstract class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.RadioChoiceHolder> {

    private Context context = null;
    public int mSelectedItem = -1;

    public ChoiceAdapter(Context context){
        this.context = context;
    }
    @Override
    public RadioChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_choice, null);

        RadioChoiceHolder radioChoiceHolder = new RadioChoiceHolder(view);
        return radioChoiceHolder;
    }

    @Override
    public int getItemCount() {
        if (ModelCart.newInstance().getSerialized() != null){
            return ModelCart.newInstance().getSerialized().questionnaire.questions.size();
        }

        return 0;
    }

    public class RadioChoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //View
        public RadioButton radioButton;
        public CheckBox checkBox;

        public RadioChoiceHolder(View itemView) {
            super(itemView);
            radioButton = (RadioButton) itemView.findViewById(R.id.radio_choice);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_choice);

            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mSelectedItem = getLayoutPosition();
            notifyItemRangeChanged(0, ModelCart.newInstance().getSerialized().questionnaire.questions.size());
        }
    }
}
