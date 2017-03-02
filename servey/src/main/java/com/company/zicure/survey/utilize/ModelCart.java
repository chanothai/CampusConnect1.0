package com.company.zicure.survey.utilize;

import com.company.zicure.survey.models.PostQuestionnaire;
import com.company.zicure.survey.post.PostSerialized;

/**
 * Created by 4GRYZ52 on 1/16/2017.
 */

public class ModelCart {

    private static ModelCart me = null;
    private PostSerialized postSerialized = null;

    private ModelCart(){
        postSerialized = new PostSerialized();
        postSerialized.questionnaire = new PostQuestionnaire();
    }

    public static ModelCart newInstance(){
        if (me == null){
            me = new ModelCart();
        }

        return me;
    }


    public PostSerialized getSerialized(){
        return postSerialized;
    }
}
