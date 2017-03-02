package com.company.zicure.survey.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 4GRYZ52 on 1/20/2017.
 */

public class PostAnswer {
    @SerializedName("answer")
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public class Answer {
        @SerializedName("questionnaire_id")
        private String questionnaireId;

        @SerializedName("section_id")
        private String sectionId;

        @SerializedName("validate")
        private Validate validate;

        public String getQuestionnaireId() {
            return questionnaireId;
        }

        public void setQuestionnaireId(String questionId) {
            this.questionnaireId = questionId;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public Validate getValidate() {
            return validate;
        }

        public void setValidate(Validate validate) {
            this.validate = validate;
        }
    }

    public class Validate{
        @Expose
        private String quiz;
        @Expose
        private String question;
        @Expose
        private String typequest;
        @Expose
        private String skip;
        @Expose
        private List<String> answer;

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getTypequest() {
            return typequest;
        }

        public void setTypequest(String typequest) {
            this.typequest = typequest;
        }

        public String getSkip() {
            return skip;
        }

        public void setSkip(String skip) {
            this.skip = skip;
        }

        public List<String> getAnswer() {
            return answer;
        }

        public void setAnswer(List<String> answer) {
            this.answer = answer;
        }
    }
}
