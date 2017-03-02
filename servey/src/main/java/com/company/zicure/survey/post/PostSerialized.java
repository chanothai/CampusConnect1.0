package com.company.zicure.survey.post;

import com.company.zicure.survey.models.PostQuestionnaire;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 1/16/2017.
 */


public class PostSerialized {
    @SerializedName("Questionnaire")
    public PostQuestionnaire questionnaire;
}
