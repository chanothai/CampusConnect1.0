package com.company.zicure.registerkey.models;

import com.company.zicure.registerkey.models.register.RegisterRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/26/2016.
 */

public class UserModel {
    @SerializedName("User")
    private String user;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
