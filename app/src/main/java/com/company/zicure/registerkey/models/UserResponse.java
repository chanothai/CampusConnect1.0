package com.company.zicure.registerkey.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 3/10/2017.
 */

public class UserResponse {
    @SerializedName("User")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        @SerializedName("citizen_id")
        private String citizenID;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("address")
        private String address;
        @SerializedName("phone")
        private String phone;
        @SerializedName("gender")
        private String gender;

        public String getCitizenID() {
            return citizenID;
        }

        public void setCitizenID(String citizenID) {
            this.citizenID = citizenID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
