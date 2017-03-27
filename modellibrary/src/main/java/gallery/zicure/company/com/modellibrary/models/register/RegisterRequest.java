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
