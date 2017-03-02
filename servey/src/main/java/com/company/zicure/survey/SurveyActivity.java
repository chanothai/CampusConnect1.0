package com.company.zicure.survey;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.company.zicure.survey.adapter.QuestionViewPagerAdapter;
import com.company.zicure.survey.fragment.QuestionFragment;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.PostAnswer;
import com.company.zicure.survey.models.PostQuestionnaire;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.network.ClientHttp;
import com.company.zicure.survey.post.PostSerialized;
import com.company.zicure.survey.utilize.EventBusCart;
import com.company.zicure.survey.utilize.GsonCart;
import com.company.zicure.survey.utilize.ModelCart;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String KEY_QUEST = "key_quest";
    private QuestionFragment questFragment;

    private QuestionViewPagerAdapter questViewPagerAdapter;
    //View

    //viewPager
    private ViewPager questPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();

        if (savedInstanceState == null) {
            ClientHttp.newInstance(this).requestQuestion();
        }

        questPager.setOnPageChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable(KEY_QUEST, Parcels.wrap(setModelQuest().questionnaire));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setModelQuest().questionnaire = Parcels.unwrap(savedInstanceState.getParcelable(KEY_QUEST));
        setViewPager();
    }

    private void bindView() {
        questPager = (ViewPager) findViewById(R.id.quest_view_pager);
    }

    private PostSerialized setModelQuest() {
        return ModelCart.newInstance().getSerialized();
    }

    private void setViewPager() {
        questViewPagerAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager());
        questPager.setAdapter(questViewPagerAdapter);
    }

    private PostQuestionnaire.PostQuestion getPostion(int position){
        position++;
        if (position > ModelCart.newInstance().getSerialized().questionnaire.questions.size()){
            return null;
        }

        position--;
        return ModelCart.newInstance().getSerialized().questionnaire.questions.get(position);
    }

    @Subscribe
    public void onEvent(QuestionResponse.Result result) {
        if (result.getSuccess().equalsIgnoreCase("OK")) {
            QuestionResponse.Questionnaire questionnaire = result.getData().getQuestionnaire();

            setModelQuest().questionnaire.sectionId = questionnaire.getSectionId();
            setModelQuest().questionnaire.questionnaireId = questionnaire.getQuestionnaireId();
            setModelQuest().questionnaire.questions = new ArrayList<PostQuestionnaire.PostQuestion>();

            for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
                PostQuestionnaire.PostQuestion postQuest = new PostQuestionnaire().new PostQuestion();
                postQuest.quiz = questionnaire.getQuestions().get(i).getQuiz();
                postQuest.question = questionnaire.getQuestions().get(i).getQuestion();
                postQuest.typequest = questionnaire.getQuestions().get(i).getTypequest();
                postQuest.skip = questionnaire.getQuestions().get(i).getSkip();

                postQuest.options = new ArrayList<String>();
                for (int j = 0; j < questionnaire.getQuestions().get(i).getOptions().size(); j++) {
                    postQuest.options.add(j, questionnaire.getQuestions().get(i).getOptions().get(j));
                }

                setModelQuest().questionnaire.questions.add(i, postQuest);
            }

            Gson gson = new Gson();
            String jsonStr = gson.toJson(setModelQuest().questionnaire);
            Toast.makeText(getApplicationContext(), jsonStr, Toast.LENGTH_SHORT).show();

            setViewPager();

        } else {
            Toast.makeText(getApplicationContext(), result.getSuccess() + " , " + result.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    private void validateData(int position){
        if (getPostion(position) != null){
            //Save position before;
            position--;
            if (position < 0){
                position = 0;
            }
            //------------------
            int valueAnswer = 0;
            for (int i = 0; i < getPostion(position).answer.size(); i++){
                //validate for answer
                if (!getPostion(position).answer.get(i).equalsIgnoreCase("")){
                    valueAnswer++;
                }
            }

            if (valueAnswer > 0){
                PostAnswer postAnswer = new PostAnswer();
                PostAnswer.Answer answer = postAnswer.new Answer();
                answer.setQuestionnaireId(ModelCart.newInstance().getSerialized().questionnaire.questionnaireId);
                answer.setSectionId(ModelCart.newInstance().getSerialized().questionnaire.sectionId);

                PostAnswer.Validate validate = postAnswer.new Validate();
                validate.setQuiz(getPostion(position).quiz);
                validate.setQuestion(getPostion(position).question);
                validate.setTypequest(getPostion(position).typequest);
                validate.setSkip(getPostion(position).skip);

                List<String> listAnswer = new ArrayList<String>();
                for (int i = 0; i < getPostion(position).answer.size(); i++){
                    listAnswer.add(i, getPostion(position).answer.get(i));
                }
                validate.setAnswer(listAnswer);

                answer.setValidate(validate);
                postAnswer.setAnswer(answer);

                Log.d("PostAnswer", new GsonCart<PostAnswer>(postAnswer).getJsonStr());
                //Post
                ClientHttp.newInstance(this).postAnswerData(postAnswer);
            }
        }
    }

    @Subscribe
    public void onEvent(AnswerResponse.Result result) {
        Toast.makeText(getApplicationContext(), result.getSuccess(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusCart.getInstance().getEventBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //Pager change
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        validateData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
