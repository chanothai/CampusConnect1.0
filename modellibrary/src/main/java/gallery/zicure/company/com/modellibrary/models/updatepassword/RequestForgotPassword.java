package gallery.zicure.company.com.modellibrary.models.updatepassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/5/2017 AD.
 */

public class RequestForgotPassword {
    @SerializedName("User") private UserUpdate userUpdate;

    public UserUpdate getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(UserUpdate userUpdate) {
        this.userUpdate = userUpdate;
    }

    public class UserUpdate {
        @SerializedName("username") private String username;
        @SerializedName("email") private String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
