package com.company.zicure.registerkey.models.otp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class OTPModel {
    @SerializedName("OTP")
    private String encryptOTP;

    public String getEncryptOTP() {
        return encryptOTP;
    }

    public void setEncryptOTP(String encryptOTP) {
        this.encryptOTP = encryptOTP;
    }
}
