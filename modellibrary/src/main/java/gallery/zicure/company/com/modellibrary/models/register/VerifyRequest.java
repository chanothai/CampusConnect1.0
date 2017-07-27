package gallery.zicure.company.com.modellibrary.models.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/26/2017 AD.
 */

public class VerifyRequest {
    @SerializedName("User") private VerifyUser user;

    public VerifyUser getUser() {
        return user;
    }

    public void setUser(VerifyUser user) {
        this.user = user;
    }

    public static class VerifyUser {
        @SerializedName("username") private String username;
        @SerializedName("pin_code") private String pinCode;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }
    }
}
