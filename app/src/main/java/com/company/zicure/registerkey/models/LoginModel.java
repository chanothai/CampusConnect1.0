package com.company.zicure.registerkey.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class LoginModel {
    @SerializedName("Login")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
