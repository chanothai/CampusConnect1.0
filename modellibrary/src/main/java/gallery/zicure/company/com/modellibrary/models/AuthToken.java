package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 3/22/2017.
 */

public class AuthToken {
    @SerializedName("auth_token")
    private String authToken;
    @SerializedName("auth_token_expiry_date")
    private String expiryDate;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
