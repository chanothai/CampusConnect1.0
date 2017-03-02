package com.company.zicure.survey.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.company.zicure.survey.R;
import com.company.zicure.survey.adapter.ChoiceAdapter;
import com.company.zicure.survey.interfaces.LaunchCallback;
import com.company.zicure.survey.models.PostQuestionnaire;
import com.company.zicure.survey.utilize.ModelCart;
import com.company.zicure.survey.utilize.NextzyUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGER = "pager";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int page;
    private String mParam2;

    //Adapter
    private ChoiceAdapter optionAdapter = null;

    //View
    private RecyclerView rvOptions = null;
    private TextView txtQuestion = null;


    public QuestionFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page Parameter 1.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(int page) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        rvOptions = (RecyclerView) root.findViewById(R.id.list_option);
        txtQuestion = (TextView) root.findViewById(R.id.txt_question);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
            String questStr = getPostion().question;
            txtQuestion.setText(questStr);

            rvOptions.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            setAdapter();
        }
    }

    public void initialAnswer(){
        if (getPostion().answer == null){
            getPostion().answer = new ArrayList<String>();
            for (int i = 0; i < getPostion().options.size(); i++){
                getPostion().answer.add(i, "");
            }
        }
    }

    private PostQuestionnaire.PostQuestion getPostion(){
        return ModelCart.newInstance().getSerialized().questionnaire.questions.get(page);
    }

    private void setAdapter(){
        initialAnswer();
        optionAdapter = new ChoiceAdapter(getActivity()) {
            @Override
            public void onBindViewHolder(final RadioChoiceHolder holder, final int position) {
                String typeQuest = getPostion().typequest;
                if (typeQuest.equalsIgnoreCase("single")){
                    holder.radioButton.setVisibility(View.VISIBLE);
                    holder.radioButton.setText(getPostion().options.get(position));
                    holder.checkBox.setVisibility(ViewPager.GONE);

                    if (getPostion().answer.get(0).equalsIgnoreCase(getPostion().options.get(position))){
                        NextzyUtil.launch(new LaunchCallback() {
                            @Override
                            public void onLaunchCallback() {
                                holder.radioButton.setChecked(true);
                            }
                        });
                    }

                    holder.radioButton.setChecked(position == mSelectedItem);
                    holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b){
                                //Save answer
                                getPostion().answer.set(0, getPostion().options.get(position));
                            }
                        }
                    });
                }

                else if (typeQuest.equalsIgnoreCase("mutiple")){
                    holder.checkBox.setVisibility(View.VISIBLE);
                    holder.checkBox.setText(getPostion().options.get(position));
                    holder.radioButton.setVisibility(View.GONE);

                    for (int i = 0; i < getPostion().answer.size(); i++){
                        String answer = getPostion().answer.get(i);
                        String option = getPostion().options.get(position);
                        if (answer.equalsIgnoreCase(option)){
                            NextzyUtil.launch(new LaunchCallback() {
                                @Override
                                public void onLaunchCallback() {
                                    holder.checkBox.setChecked(true);
                                }
                            });
                        }
                    }

                    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b){
                                //Save answer
                                getPostion().answer.set(position, getPostion().options.get(position));
                            }else{
                                getPostion().answer.set(position, "");
                            }
                        }
                    });
                }
            }

        };

        rvOptions.setAdapter(optionAdapter);
        rvOptions.setItemAnimator(new DefaultItemAnimator());

        Gson gson = new Gson();
        String jsonStr = gson.toJson(getPostion().answer);
        Log.d("AnswerResponse", jsonStr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
