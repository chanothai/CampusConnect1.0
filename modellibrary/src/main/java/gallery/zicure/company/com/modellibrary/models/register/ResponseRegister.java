package gallery.zicure.company.com.modellibrary.models.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/24/2017 AD.
 */

public class ResponseRegister {
    @SerializedName("result") private RegisterResult result;

    public RegisterResult getResult() {
        return result;
    }

    public void setResult(RegisterResult result) {
        this.result = result;
    }

    public static class RegisterResult {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("EResult") private RegisterEResult eResult;

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

        public RegisterEResult geteResult() {
            return eResult;
        }

        public void seteResult(RegisterEResult eResult) {
            this.eResult = eResult;
        }

        public static class RegisterEResult {
            @SerializedName("Message") private String message;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
