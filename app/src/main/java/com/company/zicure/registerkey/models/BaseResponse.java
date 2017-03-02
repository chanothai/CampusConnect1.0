package com.company.zicure.registerkey.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class BaseResponse {
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
    }
}
