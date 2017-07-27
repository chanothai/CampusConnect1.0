package gallery.zicure.company.com.modellibrary.models.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/24/2017 AD.
 */

public class LoginResponse {
    @SerializedName("result")
    private LoginResult result;

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    public static class LoginResult {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Data") private LoginData data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public LoginData getData() {
            return data;
        }

        public void setData(LoginData data) {
            this.data = data;
        }

        public static class LoginData {
            @SerializedName("User") private LoginUser user;

            public LoginUser getUser() {
                return user;
            }

            public void setUser(LoginUser user) {
                this.user = user;
            }


            public static class LoginUser {
                @SerializedName("dynamic_key") private String dynamicKey;
                @SerializedName("token") private String token;

                public String getDynamicKey() {
                    return dynamicKey;
                }

                public void setDynamicKey(String dynamicKey) {
                    this.dynamicKey = dynamicKey;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }
            }
        }
    }
}
