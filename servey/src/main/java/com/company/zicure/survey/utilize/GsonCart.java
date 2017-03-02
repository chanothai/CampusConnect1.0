package com.company.zicure.survey.utilize;

import com.google.gson.Gson;

/**
 * Created by 4GRYZ52 on 1/20/2017.
 */

public class GsonCart<T> {

    private static GsonCart me = null;
    private Gson gson = null;
    private String jsonStr;
    private T  object;

    public GsonCart(T object){
        gson = new Gson();
        this.object = object;
    }

    public String getJsonStr(){
        return jsonStr = gson.toJson(object);
    }
}
