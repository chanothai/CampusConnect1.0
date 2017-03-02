package com.company.zicure.registerkey.models.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/25/2016.
 */

public class RegisterRequest {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        @SerializedName("citizen_id")
        private String citizenId;
        @SerializedName("birthdate")
        private String birthDate;
        @SerializedName("phone")
        private String phone;

        public String getCitizenId() {
            return citizenId;
        }

        public void setCitizenId(String citizenId) {
            this.citizenId = citizenId;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
