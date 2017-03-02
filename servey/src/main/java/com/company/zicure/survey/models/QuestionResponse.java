package com.company.zicure.survey.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public class QuestionResponse {
    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public class Result {
        @SerializedName("Success")
        private String success;
        @SerializedName("Error")
        private String error;
        @SerializedName("Data")
        private Data data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public class Data {

        @SerializedName("questionnaire")
        private Questionnaire questionnaire;

        public Questionnaire getQuestionnaire() {
            return questionnaire;
        }

        public void setQuestionnaire(Questionnaire questionnaire) {
            this.questionnaire = questionnaire;
        }
    }

    public class Questionnaire {
        @SerializedName("section_id")
        private String sectionId;
        @SerializedName("questionnaire_id")
        private String questionnaireId;
        @SerializedName("questions")
        private List<Question> questions = null;

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getQuestionnaireId() {
            return questionnaireId;
        }

        public void setQuestionnaireId(String questionnaireId) {
            this.questionnaireId = questionnaireId;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

    }

    public class Question {
        @SerializedName("quiz")
        private String quiz;
        @SerializedName("question")
        private String question;
        @SerializedName("typequest")
        private String typequest;
        @SerializedName("skip")
        private String skip;
        @SerializedName("options")
        private List<String> options = null;

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

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
    }
}
