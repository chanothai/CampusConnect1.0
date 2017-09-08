package gallery.zicure.company.com.modellibrary.models.updatepassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/6/2017 AD.
 */

public class RequestUpdatePassword {
    @SerializedName("User") private UserForgot user;

    public UserForgot getUser() {
        return user;
    }

    public void setUser(UserForgot user) {
        this.user = user;
    }

    public class UserForgot {
        @SerializedName("username") private String username;
        @SerializedName("new_password") private String newPassword;
        @SerializedName("confirm_password") private String confirmPassword;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
}
