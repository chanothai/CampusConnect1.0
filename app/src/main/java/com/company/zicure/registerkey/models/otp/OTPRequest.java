package com.company.zicure.registerkey.models.otp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class OTPRequest {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        @SerializedName("otp")
        private String otp;

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }
}
