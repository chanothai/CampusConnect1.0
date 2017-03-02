package com.company.zicure.registerkey.models.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class LoginRequest {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("username")
        private String username;
        @SerializedName("password")
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
