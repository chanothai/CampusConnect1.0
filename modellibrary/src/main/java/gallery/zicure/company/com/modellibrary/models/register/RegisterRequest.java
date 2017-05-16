package gallery.zicure.company.com.modellibrary.models.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/25/2016.
 */

public class RegisterRequest {
    @SerializedName("User")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User{
        @SerializedName("citizen_id")
        private String citizenId;
        @SerializedName("birth_date")
        private String birthDate;
        @SerializedName("phone")
        private String phone;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("screen_name")
        private String screenName;
        private String password;
        @SerializedName("re_password")
        private String rePassword;
        @SerializedName("email")
        private String email;

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

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRePassword() {
            return rePassword;
        }

        public void setRePassword(String rePassword) {
            this.rePassword = rePassword;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
