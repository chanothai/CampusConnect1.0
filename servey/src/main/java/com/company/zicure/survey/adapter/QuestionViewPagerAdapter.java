package com.company.zicure.survey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.zicure.survey.fragment.QuestionFragment;
import com.company.zicure.survey.fragment.SubmitQuestFragment;
import com.company.zicure.survey.utilize.ModelCart;


/**
 * Created by 4GRYZ52 on 1/13/2017.
 */

public class QuestionViewPagerAdapter extends FragmentPagerAdapter {

    public QuestionViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ModelCart.newInstance().getSerialized().questionnaire.questions.size()){
            return new SubmitQuestFragment();
        }
        return QuestionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        if (ModelCart.newInstance().getSerialized() != null){
            int valueQuest = ModelCart.newInstance().getSerialized().questionnaire.questions.size();
            if (valueQuest > 0){
                return valueQuest + 1;
            }
        }
        return 0;
    }
}
