package com.company.zicure.survey.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by 4GRYZ52 on 1/16/2017.
 */

public class PostQuestionnaire {
    @SerializedName("section_id")
    public String sectionId;
    @SerializedName("questionnaire_id")
    public String questionnaireId;
    @SerializedName("questions")
    public List<PostQuestion> questions;

    public class PostQuestion {
        @SerializedName("quiz")
        public String quiz;
        @SerializedName("question")
        public String question;
        @SerializedName("typequest")
        public String typequest;
        @SerializedName("skip")
        public String skip;
        @SerializedName("options")
        public List<String> options;
        @SerializedName("answer")
        public List<String> answer = null;
    }
}
