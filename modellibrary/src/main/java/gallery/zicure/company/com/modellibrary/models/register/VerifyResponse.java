package gallery.zicure.company.com.modellibrary.models.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/26/2017 AD.
 */

public class VerifyResponse {
    @SerializedName("result") private VerifyResult result;

    public VerifyResult getResult() {
        return result;
    }

    public void setResult(VerifyResult result) {
        this.result = result;
    }

    public static class VerifyResult {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;

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
    }
}
