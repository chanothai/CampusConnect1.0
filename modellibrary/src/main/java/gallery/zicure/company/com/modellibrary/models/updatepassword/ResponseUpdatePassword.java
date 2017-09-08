package gallery.zicure.company.com.modellibrary.models.updatepassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/6/2017 AD.
 */

public class ResponseUpdatePassword {
    @SerializedName("result") private ResultForgot result;

    public ResultForgot getResult() {
        return result;
    }

    public void setResult(ResultForgot result) {
        this.result = result;
    }

    public class ResultForgot {
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
