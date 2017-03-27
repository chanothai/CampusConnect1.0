package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 3/23/2017.
 */

public class RequestUserCode {
    @SerializedName("User")
    private UserRequest user;
    @SerializedName("DeviceToken")
    private DeviceToken deviceToken;

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public void setDeviceToken(DeviceToken deviceToken) {
        this.deviceToken = deviceToken;
    }

    public static class DeviceToken {
        @SerializedName("user_code")
        private String userCode;

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }
    }
}
